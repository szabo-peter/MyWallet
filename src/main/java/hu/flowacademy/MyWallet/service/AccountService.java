package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateAccountDTO;
import hu.flowacademy.MyWallet.dto.TransferMoneyDTO;
import hu.flowacademy.MyWallet.exception.MissingIDException;
import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.model.Currency;
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
                .currency(createAccountDTO.getCurrency())
                .build());

        log.info("Created an account with this id: {}", createdAccount.getId());
        return createdAccount;
    }

    public List<Account> getAllAccounts() {
        List<Account> allAccount = accountRepository.findAll();
        log.info("Found ({}) accounts.", allAccount.size());
        return allAccount;
    }

    public Account deleteAccount(String id) {
        Account deletedAccount = accountRepository.findById(id).orElseThrow(() -> new MissingIDException("Not a valid Account ID!"));
        accountRepository.delete(deletedAccount);
        log.info("Account deleted with this id {}", id);
        return deletedAccount;
    }

    public void transferMoneyBetweenAccounts(TransferMoneyDTO transferMoneyDTO) {
        validateTransferMoneyDTO(transferMoneyDTO);
        Account sourceAccount = accountRepository.findById(transferMoneyDTO.getSourceAccountID()).orElseThrow(() -> new MissingIDException("Not a valid SourceAccount ID!"));
        Account destinationAccount = accountRepository.findById(transferMoneyDTO.getDestinationAccountID()).orElseThrow(() -> new MissingIDException("Not a valid DestinationAccount ID!"));
        if (sourceAccount.getBalance() < transferMoneyDTO.getAmount()) {
            throw new ValidationException("Not enough money in SourceAccount!");
        }
        accountRepository.save(sourceAccount.toBuilder().balance(sourceAccount.getBalance() - CurrencyConverter.convertCurrency(transferMoneyDTO.getAmount(), destinationAccount.getCurrency(), sourceAccount.getCurrency())).build());
        accountRepository.save(destinationAccount.toBuilder().balance(destinationAccount.getBalance() + CurrencyConverter.convertCurrency(transferMoneyDTO.getAmount(), destinationAccount.getCurrency(), sourceAccount.getCurrency())).build());
    }

    private void validateTransferMoneyDTO(TransferMoneyDTO transferMoneyDTO) {
        log.info("Validating createAccountDTO.");
        if (!StringUtils.hasText(transferMoneyDTO.getSourceAccountID())) {
            throw new ValidationException("Transfer needs a source Account ID!");
        }
        if (!StringUtils.hasText(transferMoneyDTO.getDestinationAccountID())) {
            throw new ValidationException("Transfer needs a destination Account ID!");
        }
        if (transferMoneyDTO.getAmount() <= 0) {
            throw new ValidationException("Transfer amount must be greater than 0!");
        }
    }

    private void validate(CreateAccountDTO createAccountDTO) {
        log.info("Validating createAccountDTO.");
        if (!StringUtils.hasText(createAccountDTO.getName())) {
            throw new ValidationException("Account needs a name!");
        }
        if (createAccountDTO.getBalance() <= 0) {
            throw new ValidationException("Account Balance must be greater than 0!");
        }
        if (createAccountDTO.getCurrency() == null) {
            throw new ValidationException("Account needs a currency!");
        }
        if (!createAccountDTO.getCurrency().equals(Currency.HUF) &&
                !createAccountDTO.getCurrency().equals(Currency.EUR) &&
                !createAccountDTO.getCurrency().equals(Currency.USD)) {
            throw new ValidationException("Not valid Currency! (Use only: USD,HUF,EUR)");
        }
    }
}
