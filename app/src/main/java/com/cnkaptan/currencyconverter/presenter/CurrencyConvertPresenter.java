package com.cnkaptan.currencyconverter.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cnkaptan.currencyconverter.BasePresenter;
import com.cnkaptan.currencyconverter.data.DataManager;
import com.cnkaptan.currencyconverter.data.model.Consumer;
import com.cnkaptan.currencyconverter.data.model.ConvertResponse;
import com.cnkaptan.currencyconverter.util.Currency;

import rx.Observer;
import rx.Scheduler;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public class CurrencyConvertPresenter extends BasePresenter<CurrencyConvertContract.View> implements CurrencyConvertContract.Presenter {
    @NonNull private final Scheduler ioScheduler;
    @NonNull private final Scheduler mainScheduler;
    @NonNull private DataManager mDatamanager;
    @NonNull private Consumer consumer;
    public CurrencyConvertPresenter(@NonNull Scheduler ioScheduler, @NonNull Scheduler mainScheduler,
                                    @NonNull DataManager mDatamanager,@NonNull Consumer consumer) {
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
        this.mDatamanager = mDatamanager;
        this.consumer = consumer;
    }

    @Override
    public void convert(final String amount, final String from, final String to) {
        getView().showLoading();
        addSubscription(mDatamanager.convert(amount, from, to)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Observer<ConvertResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("tag",e.getMessage());
                        e.printStackTrace();
                        getView().hideLoading();
                        getView().onError(e.getMessage());
                    }

                    @Override
                    public void onNext(ConvertResponse convertResponse) {
                        consumer.handleAccount(
                                Double.valueOf(amount),
                                Double.valueOf(convertResponse.getAmount()),
                                Currency.fromValue(from),
                                Currency.fromValue(to));
                        getView().hideLoading();
                    }
                }));
    }
}
