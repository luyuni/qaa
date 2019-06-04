package top.luyuni.qaa.async.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.qaa.async.EventHandler;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.model.EntityType;
import top.luyuni.qaa.model.Feed;
import top.luyuni.qaa.model.Question;
import top.luyuni.qaa.model.User;
import top.luyuni.qaa.service.IFeedService;
import top.luyuni.qaa.service.IFollowService;
import top.luyuni.qaa.service.IQuestionService;
import top.luyuni.qaa.service.IUserService;
import top.luyuni.qaa.util.JedisAdapter;
import top.luyuni.qaa.util.RedisKeyUtil;

import java.util.*;

@Component
public class FeedHandler implements EventHandler {
    @Autowired
    private IFollowService IFollowService;

    @Autowired
    private IUserService IUserService;

    @Autowired
    private IFeedService IFeedService;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    private IQuestionService IQuestionService;


    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<String ,String>();
        // 触发用户是通用的
        User actor = IUserService.getUser(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getType() == EventType.COMMENT ||
                (model.getType() == EventType.FOLLOW  && model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = IQuestionService.getById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public void doHandle(EventModel model) {
        // 为了测试，把model的userId随机一下
        Random r = new Random();
        model.setActorId(1+r.nextInt(10));

        // 构造一个新鲜事
        Feed feed = new Feed();
        feed.setCreatedDate(new Date());
        feed.setType(model.getType().getValue());
        feed.setUserId(model.getActorId());
        feed.setData(buildFeedData(model));
        if (feed.getData() == null) {
            // 不支持的feed
            return;
        }
        IFeedService.addFeed(feed);

        // 获得所有粉丝
        List<Integer> followers = IFollowService.getFollowers(EntityType.ENTITY_USER, model.getActorId(), Integer.MAX_VALUE);
        // 系统队列
        followers.add(0);
        // 给所有粉丝推事件
        for (int follower : followers) {
            String timelineKey = RedisKeyUtil.getTimelineKey(follower);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
            // 限制最长长度，如果timelineKey的长度过大，就删除后面的新鲜事
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(new EventType[]{EventType.COMMENT, EventType.FOLLOW});
    }
}
