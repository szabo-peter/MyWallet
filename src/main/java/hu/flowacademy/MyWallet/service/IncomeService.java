package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateIncomeDTO;
import hu.flowacademy.MyWallet.exception.MissingIDException;
import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.model.Income;
import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.repository.AccountRepository;
import hu.flowacademy.MyWallet.repository.IncomeCategoryRepository;
import hu.flowacademy.MyWallet.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final IncomeCategoryRepository incomeCategoryRepository;
    private final AccountRepository accountRepository;

    public IncomeService(IncomeRepository incomeRepository, IncomeCategoryRepository incomeCategoryRepository, AccountRepository accountRepository) {
        this.incomeRepository = incomeRepository;
        this.incomeCategoryRepository = incomeCategoryRepository;
        this.accountRepository = accountRepository;
    }

    public Income createIncome(CreateIncomeDTO createIncomeDTO) {
        validate(createIncomeDTO);
        Account account = accountRepository.findById(createIncomeDTO.getAccountID()).orElseThrow(() -> new MissingIDException("Didn't find account with this id."));
        accountRepository.save(account.toBuilder().balance(account.getBalance() + createIncomeDTO.getAmount()).build());
        IncomeCategory incomeCategory = incomeCategoryRepository.findById(createIncomeDTO.getIncomeCategoryID()).orElseThrow(() -> new MissingIDException("Didn't find incomeCategory with this id."));
        Income createdIncome = incomeRepository.save(Income.builder()
                .name(createIncomeDTO.getName())
                .account(account)
                .incomeCategory(incomeCategory)
                .description(createIncomeDTO.getDescription())
                .incomeTime(LocalDateTime.now())
                .amount(createIncomeDTO.getAmount())
                .build());
        log.info("Created an income with these ID: {}", createdIncome.getId());
        return createdIncome;
    }

    public List<Income> listIncomes() {
        List<Income> allIncomes = incomeRepository.findAll();
        log.info("Found ({}) incomes.", allIncomes.size());
        return allIncomes;
    }

    private void validate(CreateIncomeDTO createIncomeDTO) {
        log.info("Validating createExpenseDTO.");
        if (!StringUtils.hasText(createIncomeDTO.getIncomeCategoryID())) {
            throw new ValidationException("Income needs a category ID!");
        }
        if (!StringUtils.hasText(createIncomeDTO.getAccountID())) {
            throw new ValidationException("Income needs an account ID!");
        }
        if (!StringUtils.hasText(createIncomeDTO.getName())) {
            throw new ValidationException("Income needs a name!");
        }
        if (createIncomeDTO.getAmount() <= 0) {
            throw new ValidationException("Income amount must be greater than 0!");
        }
    }
}
