package top.luyuni.qaa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.luyuni.qaa.dao.QuestionDAO;
import top.luyuni.qaa.model.Question;

import java.util.Date;
import java.util.List;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QaaApplication.class)
public class QuestionDaoTest {
    @Autowired
    QuestionDAO questionDAO;
    @Test
    public void select(){
        List<Question> list = questionDAO.selectLatestQuestions(0, 0, 10);
        for (Question question : list) {
            System.out.println(question);
        }
    }
    @Test
    public void add(){
        for(int i = 1; i <= 100; i ++){
            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();    
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            question.setCreatedDate(date);
            question.setUserId(new Random().nextInt( 9) + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("Balaababalalalal Content %d", i));
            questionDAO.addQuestion(question);
        }
    }
}
