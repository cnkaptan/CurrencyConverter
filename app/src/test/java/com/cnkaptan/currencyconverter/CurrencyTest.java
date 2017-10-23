package com.cnkaptan.currencyconverter;

import com.cnkaptan.currencyconverter.util.Currency;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by cnkaptan on 21/10/2017.
 */

public class CurrencyTest {

    @Before
    public void init(){

    }

    @Test
    public void getValue(){

        Assert.assertEquals("Esit degil", Currency.EUR,Currency.fromValue("EUR"));
    }
}
