package com.devd.atmapi.controller;

import com.devd.atmapi.common.Request;
import com.devd.atmapi.common.Response;
import com.devd.atmapi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/account/balance/{accountNumber}/{pin}")
    public ResponseEntity<Response<String>> getAccountBalance(@PathVariable("accountNumber") String accountNumber, @PathVariable("pin") String pin) {
        Response result = accountService.getAccountBalance(accountNumber, pin);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping("/account/withdraw")
    public ResponseEntity<Response<String>> withdrawMoney(@RequestBody Request request){
        Response result = accountService.withdrawMoney(request);
        return new ResponseEntity<>(result, result.getStatus());
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<Response<String>> depositMoney(@RequestBody Request request){
        Response result = accountService.depositMoney(request);
        return new ResponseEntity<>(result, result.getStatus());
    }
}
