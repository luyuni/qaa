package top.luyuni.qaa;

import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.luyuni.qaa.dao.UserDAO;
import top.luyuni.qaa.model.User;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QaaApplication.class)
public class UserDaoTests {
    @Autowired
    UserDAO userDAO;


    @Test
    public void addUser() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDAO.addUser(user);
            user.setPassword("newpassword");
            userDAO.updatePassword(user);
        }
    }
    @Test
    public void delete(){
        userDAO.deleteById(1);
    }
    @Test
    public void update(){
        User user = new User();
        user.setId(3);
        user.setPassword("11111");
        userDAO.updatePassword(user);
    }
    @Test
    public void select(){
        User user = userDAO.selectById(2);
        System.out.println(user.getName());
    }
}
