package top.luyuni.qaa.service;

import java.util.List;

public interface IFollowService {

    /**
     * 用户关注了某个实体,可以关注问题,关注用户,关注评论等任何实体
     * @param userId  关注者
     * @param entityType  关注的实体type
     * @param entityId  关注的实体id
     * @return
     */
    boolean follow(int userId, int entityType, int entityId) ;
    /**
     * 取消关注
     * @param userId  取关的用户id
     * @param entityType 取关的实体类型
     * @param entityId 取关的实体id
     * @return
     */
    boolean unfollow(int userId, int entityType, int entityId) ;

    List<Integer> getFollowers(int entityType, int entityId, int count);

    List<Integer> getFollowers(int entityType, int entityId, int offset, int count);

    List<Integer> getFollowees(int userId, int entityType, int count) ;

    List<Integer> getFollowees(int userId, int entityType, int offset, int count) ;

    long getFollowerCount(int entityType, int entityId) ;

    long getFolloweeCount(int userId, int entityType) ;

    /**
     *  判断用户是否关注了某个实体
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    boolean isFollower(int userId, int entityType, int entityId) ;
}
