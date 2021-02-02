package com.jdbc.examples.example2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

@Repository
public class AccountDaoImpl implements AccountDao {
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public AccountDaoImpl(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionTemplate = transactionTemplate;
    }


    @Override
    public void transferWithTransactionTemplate(long amount, long accountFrom, long accountTo) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                withdrawal(amount, accountFrom);
                deposit(amount, accountTo);
            }
        });

    }

    @Override
    public void transferWithAnnotation(long amount, long accountFrom, long accountTo) {
        withdrawal(amount, accountFrom);
        deposit(amount, accountTo);
    }

    @Override
    @Transactional
    public void transferWithException(long amount, long accountFrom, long accountTo) {
        withdrawal(amount, accountFrom);
        if (amount > 100) {
            throw new IllegalArgumentException();
        }
        deposit(amount, accountTo);
    }


    @Override
    public void printAllAccount() {
        List<Map<String, Object>> accounts = jdbcTemplate.queryForList("select * from account");
        accounts.forEach(System.out::println);
    }

    public void withdrawal(long amount, long accountFrom) {
        jdbcTemplate.update("update account set balance=balance-? where id = ?", amount, accountFrom);
    }

    public void deposit(long amount, long accountTo) {
        jdbcTemplate.update("update account set balance=balance+ ? where id = ?", amount, accountTo);
    }


}
