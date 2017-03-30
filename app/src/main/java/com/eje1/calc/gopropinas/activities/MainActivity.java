package com.eje1.calc.gopropinas.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eje1.calc.gopropinas.R;
import com.eje1.calc.gopropinas.TipCalcApp;
import com.eje1.calc.gopropinas.fragments.TipHistoryListFragment;
import com.eje1.calc.gopropinas.fragments.TipHistoryListFragmentListener;
import com.eje1.calc.gopropinas.models.TipRecord;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.inputBill)
    EditText inputBill;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.inputPercentage)
    EditText inputPercentage;
    @BindView(R.id.btnIncrease)
    Button btnIncrease;
    @BindView(R.id.btnDecrease)
    Button btnDecrease;
    @BindView(R.id.btnClear)
    Button btnClear;
    @BindView(R.id.txtTip)
    TextView txtTip;

    private TipHistoryListFragmentListener fragmentListener;
    private final static int TIP_STEP_CHANGE = 1;
    private final static int DEFAULT_TIP_PRECENTAGE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager()
                                                                        .findFragmentById((R.id.fragmentList));
        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener)fragment;
        //Esta es una forma para enlazar las vistas, la cual es algo tediosa sin utilizar ButterKnife
        //inputBill = (EditText) findViewById(R.id.inputBill);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            about();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit(){
        Log.e(getLocalClassName(),"Click en submit");
        hideKeyboard();
        String strInputTotal = inputBill.getText().toString().trim();
        if (!strInputTotal.isEmpty()){
            double total = Double.parseDouble(strInputTotal);
            int tipPercentage = getTipPercentage();

            TipRecord tipRecord = new TipRecord();
            tipRecord.setBill(total);
            tipRecord.setPercentage(tipPercentage);
            tipRecord.setTimestamp(new Date());

            //double tip = total*(tipPercentage/100d);

            String strTip = String.format(getString(R.string.global_message_tip),
                                            tipRecord.getTip());
            fragmentListener.addToList(tipRecord);
            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);
        }

    }

    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease(){
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);
    }

    private void handleTipChange(int change) {
        int tipPercentage = getTipPercentage();
        tipPercentage += change;
        if (tipPercentage > 0 ){
            inputPercentage.setText(String.valueOf(tipPercentage));
        }

    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease(){
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    private int getTipPercentage() {
        int tipPercentage =DEFAULT_TIP_PRECENTAGE;
        String strInputTipPercentage = inputPercentage.getText().toString().trim();
        if (!strInputTipPercentage.isEmpty()){
            tipPercentage = Integer.parseInt(strInputTipPercentage);
        } else {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
        return tipPercentage;
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe){
            Log.e(getLocalClassName(),Log.getStackTraceString(npe));
        }

    }

    private void about() {
        TipCalcApp app = (TipCalcApp) getApplication();
        String strUrl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUrl));
        startActivity(intent);
    }
}
