package top.luyuni.qaa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import top.luyuni.qaa.dao.QuestionDAO;
import top.luyuni.qaa.model.Question;
import top.luyuni.qaa.service.IQuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    QuestionDAO questionDAO;

    @Autowired
    SensitiveServiceImpl sensitiveServiceImpl;

    public Question getById(int id) {
        return questionDAO.getById(id);
    }

    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        // 敏感词过滤
        question.setTitle(sensitiveServiceImpl.filter(question.getTitle()));
        question.setContent(sensitiveServiceImpl.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int updateCommentCount(int id, int count) {
        return questionDAO.updateCommentCount(id, count);
    }
}
