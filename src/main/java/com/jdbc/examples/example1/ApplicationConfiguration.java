package com.jdbc.examples.example1;

import com.jdbc.examples.example1.entity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Arrays;


@Configuration
@ComponentScan("com.jdbc.examples.example1")
public class ApplicationConfiguration {
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("sa");
        dataSource.setPassword("password");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.createTable();
        userService.createUser(new User(2, "Inna", "MurMur"));
        userService.printAllUser();
        System.out.println("Количество записей в таблице: " + userService.countUser());
        System.out.println("Имя пользователя с id 2 = " + userService.getUserNameById(2));
        System.out.println("Логин пользователя с id 2 = " + userService.getLoginById(2));

        System.out.println("Пользователь с id 2 = " + userService.getUserById(2));

        userService.addUsers(Arrays.asList(new User(1, "Vasya", "loginVasya"), new User(3, "Sofia", "loginSofia")));
        userService.printAllUser();
    }
}


