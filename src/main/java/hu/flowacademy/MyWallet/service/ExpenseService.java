package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateExpenseDTO;
import hu.flowacademy.MyWallet.exception.MissingIDException;
import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.*;
import hu.flowacademy.MyWallet.repository.AccountRepository;
import hu.flowacademy.MyWallet.repository.ExpenseCategoryRepository;
import hu.flowacademy.MyWallet.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ExpenseService {

    private static final double EUR_TO_HUF = 357.5;
    private static final double USD_TO_HUF = 301.0;
    private static final double EUR_TO_USD = 1.19;
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository, AccountRepository accountRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.accountRepository = accountRepository;
    }

    public Expense createExpense(CreateExpenseDTO createExpenseDTO) {
        validate(createExpenseDTO);
        Account account = accountRepository.findById(createExpenseDTO.getAccountID()).orElseThrow(() -> new MissingIDException("Didn't find account with this id."));
        accountRepository.save(account.toBuilder().balance(account.getBalance() - + convertCurrency(createExpenseDTO.getAmount(), account.getCurrency(), createExpenseDTO.getCurrency())).build());
        ExpenseCategory expenseCategory = expenseCategoryRepository.findById(createExpenseDTO.getExpenseCategoryID()).orElseThrow(() -> new MissingIDException("Didn't find incomeCategory with this id."));
        Expense createdExpense = expenseRepository.save(Expense.builder()
                .name(createExpenseDTO.getName())
                .account(account)
                .expenseCategory(expenseCategory)
                .description(createExpenseDTO.getDescription())
                .expenseTime(LocalDateTime.now())
                .amount(createExpenseDTO.getAmount())
                .currency(createExpenseDTO.getCurrency())
                .build());
        log.info("Created an expense with these ID: {}", createdExpense.getId());
        return createdExpense;
    }

    public List<Expense> listExpenses() {
        List<Expense> allExpenses = expenseRepository.findAll();
        log.info("Found ({}) expenses.", allExpenses.size());
        return allExpenses;

    }
    private double convertCurrency(double amount, Currency toCurrency, Currency fromCurrency) {
        log.info("Converting {} {} to {}.", amount, fromCurrency,toCurrency);
        if (toCurrency.equals(Currency.HUF)) {
            switch (fromCurrency) {
                case EUR:
                    return amount * EUR_TO_HUF;
                case USD:
                    return amount * USD_TO_HUF;
                case HUF:
                    return amount;
            }
        }
        else if (toCurrency.equals(Currency.USD)) {
            switch (fromCurrency) {
                case EUR:
                    return amount * EUR_TO_USD;
                case USD:
                    return amount;
                case HUF:
                    return amount * (1 / USD_TO_HUF);
            }
        }
        else {
            switch (fromCurrency) {
                case EUR:
                    return amount;
                case USD:
                    return amount * (1 / EUR_TO_USD);
                case HUF:
                    return amount * (1 / EUR_TO_HUF);
            }
        }
        return 0;
    }

    private void validate(CreateExpenseDTO createExpenseDTO) {
        log.info("Validating createExpenseDTO.");
        if (!StringUtils.hasText(createExpenseDTO.getExpenseCategoryID())) {
            throw new ValidationException("Expense needs a category ID!");
        }
        if (!StringUtils.hasText(createExpenseDTO.getAccountID())) {
            throw new ValidationException("Expense needs an account ID!");
        }
        if (!StringUtils.hasText(createExpenseDTO.getName())) {
            throw new ValidationException("Expense needs a name!");
        }
        if (createExpenseDTO.getAmount() <= 0) {
            throw new ValidationException("Expense amount must be greater than 0!");
        }
        if(createExpenseDTO.getCurrency() == null){
            throw new ValidationException("Expense needs a currency!");
        }
        if (!createExpenseDTO.getCurrency().equals(Currency.HUF) &&
                !createExpenseDTO.getCurrency().equals(Currency.EUR) &&
                !createExpenseDTO.getCurrency().equals(Currency.USD)) {
            throw new ValidationException("Not valid Currency! (Use only: USD,HUF,EUR)");
        }
    }
}
