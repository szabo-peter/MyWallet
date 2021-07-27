package hu.flowacademy.MyWallet.controller;

import hu.flowacademy.MyWallet.model.IncomeCategory;
import hu.flowacademy.MyWallet.service.IncomeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/incomeCategories")
public class IncomeCategoryController {

    private final IncomeCategoryService incomeCategoryService;

    @Autowired
    public IncomeCategoryController(IncomeCategoryService incomeCategoryService) {
        this.incomeCategoryService = incomeCategoryService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public IncomeCategory createIncomeCategory(@RequestParam(name = "name") String name) {
        log.debug("Creating an IncomeCategory with this name: {}", name);
        return incomeCategoryService.createIncomeCategory(name);
    }

    @GetMapping("")
    public List<IncomeCategory> listIncomeCategories() {
        log.debug("Listing all IncomeCategories.");
        return incomeCategoryService.listIncomeCategories();
    }


}