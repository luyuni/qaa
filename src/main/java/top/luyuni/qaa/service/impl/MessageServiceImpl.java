package top.luyuni.qaa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.dao.MessageDAO;
import top.luyuni.qaa.model.Message;
import top.luyuni.qaa.service.IMessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    MessageDAO messageDAO;

    @Autowired
    SensitiveServiceImpl sensitiveServiceImpl;

    public int addMessage(Message message) {
        message.setContent(sensitiveServiceImpl.filter(message.getContent()));
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        return messageDAO.getConversationList(userId, offset, limit);
    }

    public int getConversationUnreadCount(int userId, String conversationId) {
        return messageDAO.getConversationUnreadCount(userId, conversationId);
    }
}
