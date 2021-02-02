package com.jdbc.examples.example2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;


@Configuration
@ComponentScan("com.jdbc.examples.example2")
@EnableTransactionManagement
public class ApplicationConfiguration {
    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:db-schema.sql")
                .build();
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager manager) {
        return new TransactionTemplate(manager);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        AccountService accountService = applicationContext.getBean(AccountService.class);
        System.out.println("До переводов");
        accountService.printAllAccount();

        System.out.println("Перевод с использованием TransactionTemplate");
        accountService.transferWithTransactionTemplate(200, 1, 2);
        accountService.printAllAccount();

        System.out.println("Перевод с использованием @Transaction");
        accountService.transferWithAnnotation(100, 1, 2);
        accountService.printAllAccount();

        System.out.println("Неуспешный перевод с использованием @Transaction.");
        try {
            accountService.transferWithException(300, 1, 2);
        } catch (Exception e) {
            System.out.println("Ошибка при переводе");
        }
        accountService.printAllAccount();
    }
}


