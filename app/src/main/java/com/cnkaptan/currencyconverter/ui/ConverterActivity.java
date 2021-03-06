package com.cnkaptan.currencyconverter.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.cnkaptan.currencyconverter.CurrencyConverterApp;
import com.cnkaptan.currencyconverter.R;
import com.cnkaptan.currencyconverter.data.DataManager;
import com.cnkaptan.currencyconverter.data.model.Consumer;
import com.cnkaptan.currencyconverter.presenter.CurrencyConvertContract;
import com.cnkaptan.currencyconverter.presenter.CurrencyConvertPresenter;
import com.cnkaptan.currencyconverter.presenter.CurrencyListAdapter;
import com.cnkaptan.currencyconverter.util.Currency;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConverterActivity extends AppCompatActivity implements CurrencyConvertContract.View,Consumer.AccountListener {

    private static final String TAG = ConverterActivity.class.getSimpleName();
    @BindView(R.id.et_amount)
    EditText etAmount;
    @BindView(R.id.spn_from)
    AppCompatSpinner spnFrom;
    @BindView(R.id.spn_to)
    AppCompatSpinner spnTo;
    @BindView(R.id.btn_convert)
    Button btnConvert;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.currency_list)
    RecyclerView rvCurrencyList;

    private String fromCurrency;
    private String toCurrency;
    private String[] currencies = Currency.toStringArray();
    private Consumer consumer;
    private CurrencyListAdapter currencyListAdapter;
    private CurrencyConvertContract.Presenter presenter;
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);

        ((CurrencyConverterApp)getApplicationContext()).getApiComponent().inject(this);
        consumer = new Consumer(this);
        presenter = new CurrencyConvertPresenter(
                Schedulers.io(),
                AndroidSchedulers.mainThread(),
                dataManager,
                consumer
        );
        presenter.attachView(this);
        initViews();

    }

    private void initViews() {
        currencyListAdapter = new CurrencyListAdapter(consumer.getAccount());
        rvCurrencyList.setLayoutManager(new LinearLayoutManager(this));
        rvCurrencyList.setAdapter(currencyListAdapter);

        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,currencies);
        spnFrom.setAdapter(spinnerAdapter);
        spnTo.setAdapter(spinnerAdapter);

        spnFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fromCurrency = currencies[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toCurrency = currencies[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        etAmount.clearFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_convert)
    public void convertClickAction(){
        String amount = etAmount.getText().toString();
        if (TextUtils.isEmpty(amount)){
            Toast.makeText(getApplicationContext(), R.string.empty_ampunt,Toast.LENGTH_SHORT).show();
            return;
        }

        if (fromCurrency.equalsIgnoreCase(toCurrency)){
            Toast.makeText(getApplicationContext(), R.string.same_currency,Toast.LENGTH_SHORT).show();
            return;
        }

        if (Double.valueOf(amount) > consumer.getAmountOfCurrency(Currency.fromValue(fromCurrency))){
            Toast.makeText(getApplicationContext(), R.string.no_money,Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.convert(amount,fromCurrency,toCurrency);
    }

    @Override
    public void updateAccount(Map<Currency, Double> currencies) {
        currencyListAdapter.updateDataSet(currencies);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();
    }
}
