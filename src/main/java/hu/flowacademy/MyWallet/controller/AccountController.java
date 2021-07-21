package hu.flowacademy.MyWallet.controller;

import hu.flowacademy.MyWallet.dto.CreateAccountDTO;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody CreateAccountDTO createAccountDTO){
        log.debug("Creating an account with these params: {}",createAccountDTO);
        return accountService.createAccount(createAccountDTO);
    }
}
