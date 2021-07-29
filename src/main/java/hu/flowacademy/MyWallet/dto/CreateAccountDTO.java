package hu.flowacademy.MyWallet.dto;

import hu.flowacademy.MyWallet.model.Currency;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CreateAccountDTO {

    private String name;
    private double balance;
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
