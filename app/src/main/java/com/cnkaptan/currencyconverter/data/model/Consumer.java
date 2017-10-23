package com.cnkaptan.currencyconverter.data.model;

import com.cnkaptan.currencyconverter.util.Currency;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public class Consumer {
    private static int convertCount = 0;
    private final AccountListener accountListener;
    private Map<Currency, Double> account;

    public Consumer(AccountListener accountListener) {
        this.accountListener = accountListener;
        account = new HashMap<>();
        for (Currency currency : Currency.values()) {
            if (currency == Currency.EUR) {
                account.put(currency, 1000.0);
            } else {
                account.put(currency, 0.0);
            }
        }
    }

    public void handleAccount(double withdraw, double deposit, Currency fromCurrency, Currency toCurrency) {
        if (convertCount < 5) {
            account.put(fromCurrency, (account.get(fromCurrency) - withdraw));
            account.put(toCurrency, (account.get(toCurrency) + deposit));
        } else {
            account.put(fromCurrency, (account.get(fromCurrency) - withdraw - 0.07 * withdraw));
            account.put(toCurrency, (account.get(toCurrency) + deposit));
        }
        convertCount++;
        accountListener.updateAccount(account);
    }

    public Map<Currency, Double> getAccount() {
        return account;
    }

    public Double getAmountOfCurrency(Currency currency){
        return account.get(currency);
    }


    public interface AccountListener{
        void updateAccount(Map<Currency,Double> currencies);
    }
}
