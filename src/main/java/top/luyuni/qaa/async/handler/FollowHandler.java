package top.luyuni.qaa.async.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.qaa.async.EventHandler;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.model.EntityType;
import top.luyuni.qaa.model.Message;
import top.luyuni.qaa.model.User;
import top.luyuni.qaa.service.IMessageService;
import top.luyuni.qaa.service.IUserService;
import top.luyuni.qaa.util.QaaUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
    @Autowired
    private IMessageService IMessageService;

    @Autowired
    private IUserService IUserService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(QaaUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = IUserService.getUser(model.getActorId());

        if (model.getEntityType() == EntityType.ENTITY_QUESTION) {
            message.setContent("用户" + user.getName()
                    + "关注了你的问题,http://127.0.0.1:8080/question/" + model.getEntityId());
        } else if (model.getEntityType() == EntityType.ENTITY_USER) {
            message.setContent("用户" + user.getName()
                    + "关注了你,http://127.0.0.1:8080/user/" + model.getActorId());
        }

        IMessageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
