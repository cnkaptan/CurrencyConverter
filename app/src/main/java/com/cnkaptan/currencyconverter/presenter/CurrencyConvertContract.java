package com.cnkaptan.currencyconverter.presenter;

import com.cnkaptan.currencyconverter.MvpPresenter;
import com.cnkaptan.currencyconverter.MvpView;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public interface CurrencyConvertContract {
    interface View extends MvpView {
        void showResult(String result);
        void onError(String message);
        void showLoading();
        void hideLoading();
    }

    interface Presenter extends MvpPresenter<View> {
        void convert(String amount, String from, String to);
    }
}
