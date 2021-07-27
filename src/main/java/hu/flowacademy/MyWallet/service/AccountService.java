package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateAccountDTO;
import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        validate(createAccountDTO);
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

    private void validate(CreateAccountDTO createAccountDTO) {
        log.info("Validating createAccountDTO.");
        if(!StringUtils.hasText(createAccountDTO.getName())){
            throw new ValidationException("Account need a name!");
        }
        if(createAccountDTO.getBalance()>=0){
            throw new ValidationException("Account Balance must be greater then 0");
        }
    }
}
