package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.exception.ValidationException;
import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.repository.IncomeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class IncomeCategoryService {

    private final IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    public IncomeCategoryService(IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public IncomeCategory createIncomeCategory(String name) {
        validate(name);
        IncomeCategory createdIncomeCategory = incomeCategoryRepository.save(IncomeCategory.builder()
                .name(name)
                .build());
        log.info("Created an IncomeCategory with this id: {}", createdIncomeCategory.getId());
        return createdIncomeCategory;
    }

    public List<IncomeCategory> listIncomeCategories() {
        List<IncomeCategory> allIncomeCategory = incomeCategoryRepository.findAll();
        log.info("Found ({}) expenseCategories", allIncomeCategory.size());
        return allIncomeCategory;
    }
    private void validate(String name) {
        log.info("Validating IncomeCategory name.");
        if(!StringUtils.hasText(name)){
            throw new ValidationException("Income Category needs a name!");
        }
    }
}
