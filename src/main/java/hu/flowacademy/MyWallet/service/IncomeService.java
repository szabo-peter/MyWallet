package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.dto.CreateIncomeDTO;
import hu.flowacademy.MyWallet.exception.MissingIDException;
import hu.flowacademy.MyWallet.model.Account;
import hu.flowacademy.MyWallet.model.Income;
import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.repository.AccountRepository;
import hu.flowacademy.MyWallet.repository.IncomeCategoryRepository;
import hu.flowacademy.MyWallet.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.debug("Created an income with these ID: {}", createdIncome.getId());
        return createdIncome;
    }

    public List<Income> listIncomes() {
        List<Income> allIncomes = incomeRepository.findAll();
        log.debug("List ({}) incomes.", allIncomes.size());
        return allIncomes;
    }
}
