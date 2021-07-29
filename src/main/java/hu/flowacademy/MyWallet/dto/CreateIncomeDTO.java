package hu.flowacademy.MyWallet.dto;

import hu.flowacademy.MyWallet.model.Currency;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CreateIncomeDTO {

    private String name;
    private String incomeCategoryID;
    private String accountID;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
