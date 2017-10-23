package com.cnkaptan.currencyconverter.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 20/10/2017.
 */

public class ConvertResponse implements Parcelable {

    /**
     * amount : 232.82
     * currency : USD
     */
    private String amount;
    private String currency;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ConvertResponse{" +
                "amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.currency);
    }

    public ConvertResponse() {
    }

    protected ConvertResponse(Parcel in) {
        this.amount = in.readString();
        this.currency = in.readString();
    }

    public static final Parcelable.Creator<ConvertResponse> CREATOR = new Parcelable.Creator<ConvertResponse>() {
        @Override
        public ConvertResponse createFromParcel(Parcel source) {
            return new ConvertResponse(source);
        }

        @Override
        public ConvertResponse[] newArray(int size) {
            return new ConvertResponse[size];
        }
    };
}
