package hu.flowacademy.MyWallet.bootstrap;

import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.model.Currency;
import hu.flowacademy.MyWallet.model.ExpenseCategory;
import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class InitDataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final IncomeRepository incomeRepository;

    @Autowired
    public InitDataLoader(AccountRepository accountRepository, ExpenseCategoryRepository expenseCategoryRepository, ExpenseRepository expenseRepository, IncomeCategoryRepository incomeCategoryRepository, IncomeRepository incomeRepository) {
        this.accountRepository = accountRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.expenseRepository = expenseRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.incomeRepository = incomeRepository;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void run(String... args) {
        log.info("Starting init data loader...");
        if (accountRepository.count() == 0) {
            try {
                saveAccounts();
                saveExpenseCategories();
                saveIncomeCategories();
            } catch (Exception e) {
                log.warn("Something went wrong in InitDataLoader.");
            }
        }
    }

    private void saveExpenseCategories() {
        List<ExpenseCategory> expenseCategories = expenseCategoryRepository.saveAll(createExpenseCategories());
        log.info("Saved ({}) ExpenseCategories.", expenseCategories.size());
    }

    private List<ExpenseCategory> createExpenseCategories() {
        List<ExpenseCategory> expenseCategories = List.of(
                ExpenseCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Háztartás")
                        .build(),
                ExpenseCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Bank")
                        .build(),
                ExpenseCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Élelmiszer")
                        .build()
        );
        log.info("Created ({}) ExpenseCategories.", expenseCategories.size());
        return expenseCategories;
    }
    private void saveIncomeCategories() {
        List<IncomeCategory> incomeCategories = incomeCategoryRepository.saveAll(createIncomeCategories());
        log.info("Saved ({}) IncomeCategories.",incomeCategories.size());
    }

    private List<IncomeCategory> createIncomeCategories() {
        List<IncomeCategory> incomeCategories = List.of(
                IncomeCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Fizetés")
                        .build(),
                IncomeCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Egyéb bevétel")
                        .build(),
                IncomeCategory.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Jóváírás")
                        .build()
        );
        log.info("Created ({}) IncomeCategories.", incomeCategories.size());
        return incomeCategories;
    }

    private void saveAccounts() {
        List<Account> accounts = createAccounts();
        List<Account> savedAccounts = accountRepository.saveAll(accounts);
        log.info("Saved ({}) account.", savedAccounts.size());
    }

    private List<Account> createAccounts() {
        List<Account> accounts = List.of(
                Account.builder()
                        .balance(20000)
                        .currency(Currency.HUF)
                        .name("Bankszámla")
                        .id(UUID.randomUUID().toString())
                        .build(),
                Account.builder()
                        .balance(2000)
                        .currency(Currency.HUF)
                        .name("Pénztárca")
                        .id(UUID.randomUUID().toString()).build());
        log.info("Created ({}) accounts.", accounts.size());
        return accounts;
    }
}
