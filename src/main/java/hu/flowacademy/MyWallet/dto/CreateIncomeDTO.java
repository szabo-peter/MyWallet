package hu.flowacademy.MyWallet.dto;

import lombok.Data;

@Data
public class CreateIncomeDTO {

    private String name;
    private String incomeCategoryID;
    private String accountID;
    private double amount;
    private String description;
}
