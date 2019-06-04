package top.luyuni.qaa.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.qaa.async.EventHandler;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.model.Message;
import top.luyuni.qaa.model.User;
import top.luyuni.qaa.service.IMessageService;
import top.luyuni.qaa.service.IUserService;
import top.luyuni.qaa.util.QaaUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * LikeHandler负责处理点赞事件
 */
@Component
public class LikeHandler implements EventHandler {
    /**
     * 注入站内信服务
     */
    @Autowired
    private IMessageService IMessageService;
    /**
     * 注入用户服务
     */
    @Autowired
    private IUserService IUserService;

    @Override
    public void doHandle(EventModel model) {
        /**
         * 点完赞后给实体拥有者发站内信
         */
        Message message = new Message();
        /**
         * 当前用户发出
         */
        message.setFromId(QaaUtil.SYSTEM_USERID);
        /**
         * 发给被点赞实体的拥有者
         */
        message.setToId(model.getEntityOwnerId());
        /**
         * 设置私信时间
         */
        message.setCreatedDate(new Date());
        /**
         * 通过触发者id找到用户
         */
        User user = IUserService.getUser(model.getActorId());
        /**
         * 设置消息内容
         */
        message.setContent("用户" + user.getName()
                + "赞了你的评论,http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        /**
         * 调用站内信服务添加消息
         */
        IMessageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        /**
         * 关注了like事件
         */
        return Arrays.asList(EventType.LIKE);
    }
}
