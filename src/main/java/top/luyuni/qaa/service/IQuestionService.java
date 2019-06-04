package top.luyuni.qaa.service;

import top.luyuni.qaa.model.Question;

import java.util.List;

public interface IQuestionService {

    public Question getById(int id);

    public int addQuestion(Question question) ;

    public List<Question> getLatestQuestions(int userId, int offset, int limit);

    public int updateCommentCount(int id, int count);
}
