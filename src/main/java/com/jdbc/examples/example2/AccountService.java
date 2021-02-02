package com.jdbc.examples.example2;

import com.jdbc.examples.example2.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    @Transactional
    public void transferWithTransactionTemplate(long amount, long accountFrom, long accountTo) {
        accountDao.transferWithTransactionTemplate(amount, accountFrom, accountTo);
    }

    public void transferWithAnnotation(long amount, long accountFrom, long accountTo) {
        accountDao.transferWithAnnotation(amount, accountFrom, accountTo);
    }

    public void transferWithException(long amount, long accountFrom, long accountTo) {
        accountDao.transferWithException(amount, accountFrom, accountTo);
    }

    public void printAllAccount() {
        accountDao.printAllAccount();
    }


}
