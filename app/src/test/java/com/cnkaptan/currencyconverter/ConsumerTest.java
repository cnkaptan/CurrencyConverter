package com.cnkaptan.currencyconverter;

import com.cnkaptan.currencyconverter.data.model.Consumer;
import com.cnkaptan.currencyconverter.util.Currency;

import org.junit.Before;

import java.util.Map;

/**
 * Created by cnkaptan on 21/10/2017.
 */

public class ConsumerTest {
    Consumer consumer;
    @Before
    public void init(){
        consumer = new Consumer(new Consumer.AccountListener() {
            @Override
            public void updateAccount(Map<Currency, Double> currencies) {

            }
        });
    }
}
