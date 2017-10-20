package com.cnkaptan.currencyconverter.data;

import com.cnkaptan.currencyconverter.data.model.ConvertResponse;

import rx.Observable;

/**
 * Created by cnkaptan on 22/04/2017.
 */

public interface DataManager {

    Observable<ConvertResponse> getMovies(String amount,String from,String to);

}
