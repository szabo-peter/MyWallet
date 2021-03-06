package hu.flowacademy.MyWallet.controller;

import hu.flowacademy.MyWallet.dto.CreateAccountDTO;
import hu.flowacademy.MyWallet.dto.TransferMoneyDTO;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        log.info("Creating an account with these params: {}", createAccountDTO);
        return accountService.createAccount(createAccountDTO);
    }

    @GetMapping("")
    public List<Account> getAllAccounts() {
        log.info("Get all account.");
        return accountService.getAllAccounts();
    }

    @DeleteMapping("")
    public Account deleteAccount(@RequestParam(name = "id") String id) {
        log.info("Delete an account with this ID: {}", id);
        return accountService.deleteAccount(id);
    }

    @PostMapping("/transfer")
    public void transferMoneyBetweenAccounts(@RequestBody TransferMoneyDTO transferMoneyDTO){
        log.info("Transferring money from: {} to this: {} account ID.",transferMoneyDTO.getSourceAccountID(), transferMoneyDTO.getDestinationAccountID());
        accountService.transferMoneyBetweenAccounts(transferMoneyDTO);
    }

}
