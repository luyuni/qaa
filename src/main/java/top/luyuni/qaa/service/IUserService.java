package top.luyuni.qaa.service;


import top.luyuni.qaa.model.User;

import java.util.Map;

public interface IUserService {


    User selectByName(String name);

    Map<String, Object> register(String username, String password);

    Map<String, Object> login(String username, String password) ;

    User getUser(int id);

    void logout(String ticket);
}
