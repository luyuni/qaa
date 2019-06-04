package top.luyuni.qaa.service;

import top.luyuni.qaa.model.Comment;

import java.util.List;

public interface ICommentService {

    public List<Comment> getCommentsByEntity(int entityId, int entityType) ;

    public int addComment(Comment comment);

    public int getCommentCount(int entityId, int entityType);

    public int getUserCommentCount(int userId);

    public boolean deleteComment(int commentId);

    public Comment getCommentById(int id);
}
