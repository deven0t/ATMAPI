package com.devd.atmapi.controller;

import com.devd.atmapi.common.Response;
import com.devd.atmapi.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    private String getBalanceUrl = "/api/account/balance/";
    private String withdrawUrl = "/api/account/withdraw";

    @Test
    public void getAccountBalanceSuccessCase() throws Exception {
        String balance = "200";
        Mockito.when(accountService.getAccountBalance(any(), any()))
                .thenReturn(new Response<String>(HttpStatus.OK, "Money Withdrawn from Account, current Balance", balance));

        mvc.perform(get(getBalanceUrl + "123456789/1234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(balance));
    }

    @Test
    public void getAccountBalanceWhenInvalidAccountNumber() throws Exception {
        Mockito.when(accountService.getAccountBalance(any(), any()))
                .thenReturn(new Response<>(HttpStatus.BAD_REQUEST, "Account not found", null));

        mvc.perform(get(getBalanceUrl + "123/1234"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void getAccountBalanceWhenInvalidPIN() throws Exception {
        Mockito.when(accountService.getAccountBalance(any(), any()))
                .thenReturn(new Response<>(HttpStatus.UNAUTHORIZED, "Invalid PIN", null));

        mvc.perform(get(getBalanceUrl + "123/1234"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    public void withdrawMoneySuccess() throws Exception {
        Mockito.when(accountService.withdrawMoney(any()))
                .thenReturn(new Response<>(HttpStatus.OK, "Money withdrawn", "200"));

        mvc.perform(post(withdrawUrl)
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void withdrawMoneyInvalidAccountNumber() throws Exception {
        Mockito.when(accountService.withdrawMoney(any()))
                .thenReturn(new Response<>(HttpStatus.BAD_REQUEST, "Please send valid account Number", null));

        mvc.perform(post(withdrawUrl)
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    public void withdrawMoneyInvalidPIN() throws Exception {
        Mockito.when(accountService.withdrawMoney(any()))
                .thenReturn(new Response<>(HttpStatus.UNAUTHORIZED, "Invalid PIN", null));

        mvc.perform(post(withdrawUrl)
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.data").isEmpty());
    }


}