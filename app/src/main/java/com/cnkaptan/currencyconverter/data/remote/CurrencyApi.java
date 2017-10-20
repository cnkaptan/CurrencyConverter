package com.cnkaptan.currencyconverter.data.remote;

import com.cnkaptan.currencyconverter.data.model.ConvertResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public interface CurrencyApi {
    @GET("exchange/{fromAmount}-{fromCurrency}/{toCurrency}/latest")
    Observable<ConvertResponse> convert(@Path("fromAmount")String fromAmount,@Path("fromCurrency")String fromCurrency,
                                        @Path("toCurrency")String toCurrency);
}
