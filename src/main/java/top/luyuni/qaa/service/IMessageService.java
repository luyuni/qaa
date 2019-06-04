package top.luyuni.qaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.dao.MessageDAO;
import top.luyuni.qaa.model.Message;

import java.util.List;

public interface IMessageService {

    public int addMessage(Message message);

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) ;

    public List<Message> getConversationList(int userId, int offset, int limit) ;

    public int getConversationUnreadCount(int userId, String conversationId);
}
