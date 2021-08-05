package hu.flowacademy.MyWallet.dto;

import hu.flowacademy.MyWallet.model.Currency;
import lombok.Data;

@Data
public class TransferMoneyDTO {
    private String sourceAccountID;
    private String destinationAccountID;
    private double amount;
}
