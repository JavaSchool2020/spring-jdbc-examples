package com.jdbc.examples.example1.dao;

import com.jdbc.examples.example1.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate parameterJdbcTemplate;


    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.parameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    @Override
    public void createUserTable() {
        System.out.println("Создаем таблицу user");
        jdbcTemplate.update("create table user(" +
                "id number(3)," +
                "name varchar2(256)," +
                "login varchar2(256)" +
                ")");
    }

    @Override
    public void printAllUser() {
        List<Map<String, Object>> users = jdbcTemplate.queryForList("select * from user");
        users.forEach(System.out::println);
    }

    @Override
    public Integer countUsers() {
        return jdbcTemplate.queryForObject("select count(1) from user", Integer.class);
    }

    @Override
    public String getUserNameById(long id) {
        return jdbcTemplate.queryForObject("select name from user where id = ?", new Object[]{id}, String.class);
    }

    @Override
    public String getLoginById(long id) {
        return parameterJdbcTemplate.queryForObject("select login from user where id = :id", Collections.singletonMap("id", id), String.class);
    }

    @Override
    public void createUser(User user) {
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        int update = parameterJdbcTemplate.update("insert into user (id, name, login) values (:id, :name, :login)", sqlParameterSource);
        System.out.println("Сохранили " + update + " запись(ей)");
    }

    /**
     * Используем BeanPropertyRowMapper для маппинга результата запроса на поля  User
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(long id) {
        List<User> users = parameterJdbcTemplate.query("select id, name, login from user where id = :id", new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(User.class));
        return users.get(0);
    }

    /**
     * Используем RowMapper для ручного маппинга результата запроса на поля  User
     *
     * @param id
     * @return
     */
    @Override
    public User getUserByIdWithRowMap(long id) {
        List<User> users = parameterJdbcTemplate.query("select id, name, login from user where id = :id", new MapSqlParameterSource("id", id),
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));

                    }
                });

        return users.get(0);
    }

    /**
     * Вставка пачками
     *
     * @param users
     */
    @Override
    public void addUsers(List<User> users) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(users);
        parameterJdbcTemplate.batchUpdate("insert into user (id, name, login) values(:id, :name, :login)", params);
    }


}
