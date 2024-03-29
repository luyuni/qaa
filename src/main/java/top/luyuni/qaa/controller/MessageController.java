package top.luyuni.qaa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.luyuni.qaa.model.HostHolder;
import top.luyuni.qaa.model.Message;
import top.luyuni.qaa.model.User;
import top.luyuni.qaa.model.ViewObject;
import top.luyuni.qaa.service.IMessageService;
import top.luyuni.qaa.service.IUserService;
import top.luyuni.qaa.util.QaaUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private IMessageService IMessageService;

    @Autowired
    private IUserService IUserService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getConversationList(Model model) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = IMessageService.getConversationList(localUserId, 0, 10);
        List<ViewObject> conversations = new ArrayList<ViewObject>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", IUserService.getUser(targetId));
            vo.set("unread", IMessageService.getConversationUnreadCount(localUserId, message.getConversationId()));
            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<Message> messageList = IMessageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<ViewObject>();
            for (Message message : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", IUserService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取详情失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return QaaUtil.getJSONString(999, "未登录");
            }

            User user = IUserService.selectByName(toName);
            if (user == null) {
                return QaaUtil.getJSONString(1, "用户不存在");
            }

            Message message = new Message();
            message.setCreatedDate(new Date());
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            IMessageService.addMessage(message);
            return QaaUtil.getJSONString(0);

        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return QaaUtil.getJSONString(1, "发信失败");
        }
    }
}
