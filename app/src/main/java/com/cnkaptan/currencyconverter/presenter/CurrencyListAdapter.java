package com.cnkaptan.currencyconverter.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnkaptan.currencyconverter.util.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cnkaptan on 21/10/2017.
 */

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {
    private Map<Currency,Double> accountCurrency;
    private List<Currency> keyList;
    public CurrencyListAdapter(Map<Currency, Double> accountCurrency) {
        this.accountCurrency = accountCurrency;
        keyList = new ArrayList<>(accountCurrency.keySet());
    }

    public void updateDataSet(Map<Currency, Double> accountCurrency){
        this.accountCurrency = accountCurrency;
        keyList.clear();
        keyList.addAll(accountCurrency.keySet());
        notifyDataSetChanged();
    }

    @Override
    public CurrencyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyViewHolder holder, int position) {
        final Currency currency = keyList.get(position);
        final Double amount = accountCurrency.get(currency);
        holder.textView.setText(String.format("%s - %s",currency.getValue(),String.valueOf(amount)));

    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public CurrencyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(android.R.id.text1);
        }
    }
}
