package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.repository.IncomeCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
