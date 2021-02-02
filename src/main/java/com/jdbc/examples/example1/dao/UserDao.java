package com.jdbc.examples.example1.dao;

import com.jdbc.examples.example1.entity.User;

import java.util.List;

public interface UserDao {
    void createUserTable();

    void printAllUser();

    Integer countUsers();

    String getUserNameById(long id);

    String getLoginById(long id);

    void createUser(User user);

    User getUserById(long id);

    User getUserByIdWithRowMap(long id);

    void addUsers(List<User> users);
}
