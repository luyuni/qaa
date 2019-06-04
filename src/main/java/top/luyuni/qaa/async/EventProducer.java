package top.luyuni.qaa.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.util.JedisAdapter;
import top.luyuni.qaa.util.RedisKeyUtil;

@Service
public class EventProducer {
    @Autowired
    private JedisAdapter jedisAdapter;
    /**
     * 发事件,把事件添加进队列
     * @param eventModel
     * @return
     */
    public boolean fireEvent(EventModel eventModel) {
        try {
            /**
             * 把事件发生现场进行序列化push进异步消息队列
             */
            String json = JSONObject.toJSONString(eventModel);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
