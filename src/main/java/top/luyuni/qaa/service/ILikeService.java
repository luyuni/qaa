package top.luyuni.qaa.service;

public interface ILikeService {

    long getLikeCount(int entityType, int entityId);

    int getLikeStatus(int userId, int entityType, int entityId);

    long like(int userId, int entityType, int entityId);

    long disLike(int userId, int entityType, int entityId) ;
}
