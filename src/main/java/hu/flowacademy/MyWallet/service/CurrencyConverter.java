package hu.flowacademy.MyWallet.service;

import hu.flowacademy.MyWallet.model.Currency;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyConverter {
    private static final double EUR_TO_HUF = 357.5;
    private static final double USD_TO_HUF = 301.0;
    private static final double EUR_TO_USD = 1.19;

    public static double convertCurrency(double amount, Currency toCurrency, Currency fromCurrency) {
        log.info("Converting {} {} to {}.", amount, fromCurrency, toCurrency);
        if (toCurrency.equals(Currency.HUF)) {
            switch (fromCurrency) {
                case EUR:
                    return amount * EUR_TO_HUF;
                case USD:
                    return amount * USD_TO_HUF;
                case HUF:
                    return amount;
            }
        } else if (toCurrency.equals(Currency.USD)) {
            switch (fromCurrency) {
                case EUR:
                    return amount * EUR_TO_USD;
                case USD:
                    return amount;
                case HUF:
                    return amount * (1 / USD_TO_HUF);
            }
        } else {
            switch (fromCurrency) {
                case EUR:
                    return amount;
                case USD:
                    return amount * (1 / EUR_TO_USD);
                case HUF:
                    return amount * (1 / EUR_TO_HUF);
            }
        }
        return 0;
    }
}
