package top.luyuni.qaa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventProducer;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.model.Comment;
import top.luyuni.qaa.model.EntityType;
import top.luyuni.qaa.model.HostHolder;
import top.luyuni.qaa.service.ICommentService;
import top.luyuni.qaa.service.IQuestionService;
import top.luyuni.qaa.util.QaaUtil;

import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ICommentService ICommentService;

    @Autowired
    private IQuestionService IQuestionService;

    @Autowired
    private EventProducer eventProducer;


    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() != null) {
                comment.setUserId(hostHolder.getUser().getId());
            } else {
                comment.setUserId(QaaUtil.ANONYMOUS_USERID);
            }
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            ICommentService.addComment(comment);

            int count = ICommentService.getCommentCount(comment.getEntityId(), comment.getEntityType());
            IQuestionService.updateCommentCount(comment.getEntityId(), count);

            eventProducer.fireEvent(new EventModel(EventType.COMMENT).setActorId(comment.getUserId())
                    .setEntityId(questionId));

        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }
}
