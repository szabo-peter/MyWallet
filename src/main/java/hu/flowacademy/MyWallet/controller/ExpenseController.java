package hu.flowacademy.MyWallet.controller;

import hu.flowacademy.MyWallet.dto.CreateExpenseDTO;
import hu.flowacademy.MyWallet.model.Expense;
import hu.flowacademy.MyWallet.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(@RequestBody CreateExpenseDTO createExpenseDTO) {
        log.info("Creating an Expense with these params: {}", createExpenseDTO);
        return expenseService.createExpense(createExpenseDTO);
    }

    @GetMapping("")
    public List<Expense> listExpenses() {
        log.info("Listing all expenses.");
        return expenseService.listExpenses();
    }


}
