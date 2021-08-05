package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateExpenseDTO;
import hu.flowacademy.MyWallet.exception.MissingIDException;
import hu.flowacademy.MyWallet.exception.NotEnoughBalanceException;
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
        if(account.getBalance()<createExpenseDTO.getAmount()){
            throw new NotEnoughBalanceException("Not enough balance for this Expense!");
        }
        accountRepository.save(account.toBuilder().balance(account.getBalance() - CurrencyConverter.convertCurrency(createExpenseDTO.getAmount(), account.getCurrency(), createExpenseDTO.getCurrency())).build());
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

    public Expense deleteExpense(String id) {
        Expense deletedExpense = expenseRepository.findById(id).orElseThrow(() -> new MissingIDException("Give a valid Expense ID!"));
        Account account = deletedExpense.getAccount();
        accountRepository.save(account.toBuilder().balance(account.getBalance() + CurrencyConverter.convertCurrency(deletedExpense.getAmount(), account.getCurrency(), deletedExpense.getCurrency())).build());
        expenseRepository.delete(deletedExpense);
        log.info("Deleted an Expense with this ID: {}", id);
        return deletedExpense;
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
        if (createExpenseDTO.getCurrency() == null) {
            throw new ValidationException("Expense needs a currency!");
        }
        if (!createExpenseDTO.getCurrency().equals(Currency.HUF) &&
                !createExpenseDTO.getCurrency().equals(Currency.EUR) &&
                !createExpenseDTO.getCurrency().equals(Currency.USD)) {
            throw new ValidationException("Not valid Currency! (Use only: USD,HUF,EUR)");
        }
    }
}
