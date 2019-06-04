package top.luyuni.qaa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.luyuni.qaa.dao.FeedDAO;
import top.luyuni.qaa.model.Feed;

import java.util.List;

public interface IFeedService {

    /**
     * 拉模式
     * @param maxId
     * @param userIds
     * @param count
     * @return
     */
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count);

    public boolean addFeed(Feed feed) ;

    /**
     * 推模式
     * @param id
     * @return
     */
    public Feed getById(int id);
}
