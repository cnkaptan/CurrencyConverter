package com.cnkaptan.currencyconverter.data;

import android.support.annotation.NonNull;

import com.cnkaptan.currencyconverter.data.model.ConvertResponse;
import com.cnkaptan.currencyconverter.data.remote.CurrencyApi;

import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;


@Singleton
public class DataManagerImpl implements DataManager {


    private static final String TAG = DataManagerImpl.class.getSimpleName();
    @NonNull
    private final CurrencyApi currencyApi;

    public DataManagerImpl(@NonNull CurrencyApi currencyApi) {
        this.currencyApi = currencyApi;
    }


    @Override
    public Observable<ConvertResponse> getMovies(String amount, String from, String to) {
        return currencyApi.convert(amount, from, to)
                .subscribeOn(Schedulers.io());
    }
}
