package top.luyuni.qaa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.dao.FeedDAO;
import top.luyuni.qaa.model.Feed;
import top.luyuni.qaa.service.IFeedService;

import java.util.List;

@Service
public class FeedServiceImpl implements IFeedService {
    @Autowired
    FeedDAO feedDAO;

    /**
     * 拉模式
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectUserFeeds(maxId, userIds, count);
    }

    public boolean addFeed(Feed feed) {
        feedDAO.addFeed(feed);
        return feed.getId() > 0;
    }

    /**
     * 推模式
     * @param id
     * @return
     */
    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }
}
