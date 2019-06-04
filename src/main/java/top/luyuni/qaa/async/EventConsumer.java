package top.luyuni.qaa.async;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.util.JedisAdapter;
import top.luyuni.qaa.util.RedisKeyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Event和Handler的关系由consumer维护
 * 将Event分发到不同的Handler处理
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。
 * ApplicationContextAware接口为bean提供了获取当前正在运行ApplicationContext对象的方式
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    /**
     * key  从队列取出的事件Evnet
     * value那些关注事件Handler
     */
    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    /**
     * Spring应用程序上下文，用于找出被容器管理的所有Handler
     */
    private ApplicationContext applicationContext;

    /**
     *
     */
    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 找出容器中所有实现该接口的类EventHandler
         */
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                /**
                 * 遍历所有的Handler拿到每个Handler关注的事件类型
                 */
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();
                /**
                 * 遍历拿到的事件类型，注册这些事件
                 */
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        // 如果map中没有这个key 第一次注册这个时间
                        // 则new一个ArrayList对象初始化关注该事件的Handler列表
                        config.put(type, new ArrayList<EventHandler>());
                    }
                    //添加进关注该事件的Handler列表
                    config.get(type).add(entry.getValue());
                }
            }
        }
        /**
         * 该线程   取异步时间队列
         */
        Thread thread = new Thread(() -> {
            while(true) {
                /**
                 * 拿到EventQueue的key
                 */
                String key = RedisKeyUtil.getEventQueueKey();
                /**
                 * 从队列中取出事件
                 * 这是一个阻塞操作，若当前队列为空则会一直阻塞在这里
                 */
                List<String> events = jedisAdapter.brpop(0, key);

                for (String message : events) {
                    /**
                     * brpop 返回的类型是 key和value   要把key过滤掉
                     */
                    if (message.equals(key)) {
                        continue;
                    }
                    /**
                     * 将事件转成实体
                     */
                    EventModel eventModel = JSON.parseObject(message, EventModel.class);
                    /**
                     * 看注册中心是否有这个事件
                     */
                    if (!config.containsKey(eventModel.getType())) {
                        logger.error("不能识别的事件");
                        continue;
                    }
                    /**
                     * 找到这个事件，Handler开始处理事件
                     */
                    for (EventHandler handler : config.get(eventModel.getType())) {
                        handler.doHandle(eventModel);
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
