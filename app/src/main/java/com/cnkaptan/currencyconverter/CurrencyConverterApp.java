package com.cnkaptan.currencyconverter;

import android.app.Application;
import android.support.annotation.NonNull;

import com.cnkaptan.currencyconverter.di.ApiComponent;
import com.cnkaptan.currencyconverter.di.ApiModule;
import com.cnkaptan.currencyconverter.di.DaggerApiComponent;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public class CurrencyConverterApp extends Application {
    private ApiComponent apiComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        apiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule())
                .build();
    }

    @NonNull
    public ApiComponent getApiComponent(){
        return this.apiComponent;
    }
}
