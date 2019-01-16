package com.devd.atmapi.service;

import com.devd.atmapi.common.Request;
import com.devd.atmapi.common.Response;
import com.devd.atmapi.controller.AccountController;
import com.devd.atmapi.model.Account;
import com.devd.atmapi.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    Logger logger = LoggerFactory.getLogger(AccountController.class);


    public Response<String> getAccountBalance(String accountNumber, String pin) {
        Account account = accountRepository.findById(accountNumber).orElse(null);

        if (account == null) {
            logger.info("Account not fount for account no: ", accountNumber);
            return new Response<>(HttpStatus.BAD_REQUEST, "Invalid Account", null);
        } else if (!verifyPin(account, pin)) {
            return new Response<>(HttpStatus.UNAUTHORIZED, "Invalid PIN", null);
        }
        return new Response<>(HttpStatus.OK, "", account.getAccountBalance().toString());
    }

    @Transactional
    public Response<String> withdrawMoney(Request request) {
        logger.debug("Money withdraw started", request.getAccount_number());
        Account account = accountRepository.findById(request.getAccount_number()).orElse(null);
        if (account == null) {
            logger.info("Account not fount for account no: ", request.getAccount_number());
            return new Response<>(HttpStatus.BAD_REQUEST, "Invalid Account", null);
        } else if (!verifyPin(account, request.getPin())) {
            return new Response<>(HttpStatus.UNAUTHORIZED, "Invalid PIN", null);
        }
        Integer currentBalance = account.getAccountBalance();
        Integer amount = Integer.parseInt(request.getAmount());
        if (currentBalance < amount) {
            return new Response<>(HttpStatus.BAD_REQUEST, "Insufficient Balance", null);
        }
        account.setAccountBalance(currentBalance - amount);
        accountRepository.save(account);
        logger.debug("Money withdrawn from account", request.getAccount_number());
        return new Response<>(HttpStatus.OK, "Money Withdrawn from Account, current Balance", account.getAccountBalance().toString());
    }


    @Transactional
    public Response depositMoney(Request request) {
        logger.debug("Money deposit started", request.getAccount_number());
        Account account = accountRepository.findById(request.getAccount_number()).orElse(null);

        if (account == null) {
            logger.info("Account not fount for account no: ", request.getAccount_number());
            return new Response<>(HttpStatus.BAD_REQUEST, "Invalid Account", null);
        } else if (!verifyPin(account, request.getPin())) {
            return new Response<>(HttpStatus.UNAUTHORIZED, "Invalid PIN", null);
        }

        Integer currentBalance = account.getAccountBalance();
        Integer amount = Integer.parseInt(request.getAmount());
        account.setAccountBalance(currentBalance + amount);
        accountRepository.save(account);
        logger.debug("Money deposited in account", request.getAccount_number());
        return new Response<>(HttpStatus.OK, "Money Deposited, current balance", account.getAccountBalance().toString());
    }

    private boolean verifyPin(Account account, String pin) {
        if (account == null) {
            logger.info("Account should not be null");
            return false;
        }
        if (account.getPin().equals(pin))
            return true;
        logger.info("Invalid PIN for account no: ", account.getAccountNumber());
        return false;
    }

}
