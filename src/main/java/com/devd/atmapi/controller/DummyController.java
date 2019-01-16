package com.devd.atmapi.controller;

import com.devd.atmapi.model.Account;
import com.devd.atmapi.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class DummyController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<Account> getUser(@PathVariable("accountNumber") String accountNumber) {
        Optional<Account> account = accountRepository.findById(accountNumber);

        if (account == null) {
            return new ResponseEntity("Not Found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(account, HttpStatus.OK);
    }

    @GetMapping("/account")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = (List<Account>) accountRepository.findAll();

        return new ResponseEntity(accounts, HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity<Account> AddAccount(@Valid @RequestBody Account account) {
        return new ResponseEntity(accountRepository.save(account), HttpStatus.OK);
    }

    @GetMapping("/")
    @ResponseBody
    public String Status() {
        return "All Set";
    }
}
