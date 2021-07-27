package hu.flowacademy.MyWallet.controller;

import hu.flowacademy.MyWallet.dto.CreateIncomeDTO;
import hu.flowacademy.MyWallet.model.Income;
import hu.flowacademy.MyWallet.service.IncomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    @Autowired
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Income createIncome(@RequestBody CreateIncomeDTO createIncomeDTO) {
        log.debug("Creating an Income with these params: {}", createIncomeDTO);
        return incomeService.createIncome(createIncomeDTO);
    }

    @GetMapping("")
    public List<Income> listIncomes() {
        log.debug("List all incomes.");
        return incomeService.listIncomes();
    }
}
