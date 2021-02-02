package com.jdbc.examples.example2.dao;

public interface AccountDao {

    void transferWithTransactionTemplate(long amount, long accountFrom, long accountTo);

    void transferWithAnnotation(long amount, long accountFrom, long accountTo);

    void transferWithException(long amount, long accountFrom, long accountTo);

    void printAllAccount();
}
