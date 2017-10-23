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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConverterActivity extends AppCompatActivity implements CurrencyConvertContract.View {

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
    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);

        ((CurrencyConverterApp)getApplicationContext()).getApiComponent().inject(this);
        consumer = new Consumer(new Consumer.AccountListener() {
            @Override
            public void updateAccount(Map<Currency, Double> currencies) {
                currencyListAdapter.updateDataSet(currencies);
            }
        });

        currencyListAdapter = new CurrencyListAdapter(consumer.getAccount());
        rvCurrencyList.setLayoutManager(new LinearLayoutManager(this));
        rvCurrencyList.setAdapter(currencyListAdapter);

        final CurrencyConvertContract.Presenter presenter = new CurrencyConvertPresenter(
                Schedulers.io(),
                AndroidSchedulers.mainThread(),
                dataManager,
                consumer
        );

        presenter.attachView(this);

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


        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString();
                if (TextUtils.isEmpty(amount)){
                    Toast.makeText(getApplicationContext(),"Amount bos",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fromCurrency.equalsIgnoreCase(toCurrency)){
                    Toast.makeText(getApplicationContext(),"Ayni Tip",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Double.valueOf(amount) > consumer.getAmountOfCurrency(Currency.fromValue(fromCurrency))){
                    Toast.makeText(getApplicationContext(),"Hesapta Para Yok",Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.convert(amount,fromCurrency,toCurrency);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        etAmount.clearFocus();
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
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
}
