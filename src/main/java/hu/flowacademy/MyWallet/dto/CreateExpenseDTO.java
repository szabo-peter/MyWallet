package hu.flowacademy.MyWallet.dto;

import lombok.Data;

@Data
public class CreateExpenseDTO {

    private String name;
    private String expenseCategoryID;
    private String accountID;
    private double amount;
    private String description;
}
