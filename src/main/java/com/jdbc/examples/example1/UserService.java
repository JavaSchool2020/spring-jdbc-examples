package com.jdbc.examples.example1;

import com.jdbc.examples.example1.dao.UserDao;
import com.jdbc.examples.example1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserService {

    private UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createTable() {
        System.out.println("Создаем таблицу user");
        userDao.createUserTable();
    }

    public void printAllUser() {
        System.out.println("В таблице user содержатся следующие пользователи: ");
        userDao.printAllUser();
    }

    public Integer countUser() {
        return userDao.countUsers();
    }

    public String getUserNameById(long id) {
        return userDao.getUserNameById(id);
    }

    public String getLoginById(long id) {
        return userDao.getLoginById(id);
    }

    public void createUser(User user) {
        userDao.createUser(user);
    }

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    public void addUsers(List<User> users) {
        System.out.println("Добавляем пользователей: " + users);
        userDao.addUsers(users);
    }
}
