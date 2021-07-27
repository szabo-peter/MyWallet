package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.ExpenseCategory;
import hu.flowacademy.MyWallet.repository.ExpenseCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseCategory createExpenseCategory(String name) {
        validate(name);
        ExpenseCategory savedExpenseCategory = expenseCategoryRepository.save(ExpenseCategory.builder()
                .name(name)
                .build());
        log.info("Created an ExpenseCategory with this id: {}", savedExpenseCategory.getId());
        return savedExpenseCategory;
    }

    public List<ExpenseCategory> listExpenseCategories() {
        List<ExpenseCategory> allExpenseCategory = expenseCategoryRepository.findAll();
        log.info("Found ({}) expenseCategories", allExpenseCategory.size());
        return allExpenseCategory;
    }

    private void validate(String name) {
        log.info("Validating ExpenseCategory name.");
        if(!StringUtils.hasText(name)){
            throw new ValidationException("Expense Category needs a name!");
        }
    }
}
