package com.zb.Account.controller;

import com.zb.Account.domain.Account;
import com.zb.Account.dto.CreateAccount;
import com.zb.Account.service.AccountService;
import com.zb.Account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @PostMapping("/account")
    public String createAccount(@RequestBody @Valid CreateAccount.Request request){
        accountService.createAccount(request.getUserId(), request.getInitialBalance());
        return "success";
    }

    @GetMapping("/get-lock")
    public String getLock(){
        return redisTestService.getLock();
    }

    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
}
