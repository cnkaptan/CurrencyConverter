package com.cnkaptan.currencyconverter.di;

import com.cnkaptan.currencyconverter.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cnkaptan on 08/04/2017.
 */
@Singleton
@Component(modules = {AppModule.class,ApiModule.class})
public interface ApiComponent {

}
