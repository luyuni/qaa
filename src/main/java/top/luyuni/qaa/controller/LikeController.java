package top.luyuni.qaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventProducer;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.model.Comment;
import top.luyuni.qaa.model.EntityType;
import top.luyuni.qaa.model.HostHolder;
import top.luyuni.qaa.service.ICommentService;
import top.luyuni.qaa.service.ILikeService;
import top.luyuni.qaa.util.QaaUtil;

@Controller
public class LikeController {
    @Autowired
    private ILikeService ILikeService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ICommentService ICommentService;

    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return QaaUtil.getJSONString(999);
        }

        Comment comment = ICommentService.getCommentById(commentId);

        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId()).setEntityId(commentId)
                .setEntityType(EntityType.ENTITY_COMMENT).setEntityOwnerId(comment.getUserId())
                .setExt("questionId", String.valueOf(comment.getEntityId())));

        long likeCount = ILikeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return QaaUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return QaaUtil.getJSONString(999);
        }

        long likeCount = ILikeService.disLike(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, commentId);
        return QaaUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
