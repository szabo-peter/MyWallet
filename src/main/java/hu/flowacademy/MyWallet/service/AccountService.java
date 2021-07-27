package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateAccountDTO;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(CreateAccountDTO createAccountDTO) {
        Account createdAccount = accountRepository.save(Account.builder()
                .name(createAccountDTO.getName())
                .balance(createAccountDTO.getBalance())
                .build());

        log.info("Created an account with this id: {}", createdAccount.getId());
        return createdAccount;
    }

    public List<Account> getAllAccounts() {
        List<Account> allAccount = accountRepository.findAll();
        log.info("Found ({}) accounts.", allAccount.size());
        return allAccount;
    }
}
