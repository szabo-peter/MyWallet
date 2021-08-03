package hu.flowacademy.MyWallet.controller;


import hu.flowacademy.MyWallet.model.ExpenseCategory;
import hu.flowacademy.MyWallet.service.ExpenseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/expenseCategories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    @Autowired
    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseCategory createExpenseCategory(@RequestParam(name = "name") String name) {
        log.info("Creating an ExpenseCategory with this name: {}", name);
        return expenseCategoryService.createExpenseCategory(name);
    }

    @GetMapping("")
    public List<ExpenseCategory> listExpenseCategories() {
        log.info("Listing all ExpenseCategories.");
        return expenseCategoryService.listExpenseCategories();
    }

    @DeleteMapping("")
    public ExpenseCategory deleteExpenseCategory(@RequestParam(name = "id") String id) {
        log.info("Delete an ExpenseCategory ith this ID: {}", id);
        return expenseCategoryService.deleteExpenseCategory(id);
    }
}
