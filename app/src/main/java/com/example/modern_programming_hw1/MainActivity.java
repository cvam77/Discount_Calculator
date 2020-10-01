package com.example.modern_programming_hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public TextView mDiscountPercent;
    public TextView mSalePrice;
    public TextView mListPrice;

    public EditText mDiscountPercentEditText;
    public EditText mListPriceEditText;
    public EditText mSalePriceEditText;

    public Button mClearButton;
    public Button mCalculateButton;

    public TextView mResultTextview;

    int spinnerSelected = 0;

    double discountAmount, listPriceAmount, salePriceAmount, discountPercentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultTextview = findViewById(R.id.results);
        mDiscountPercent = findViewById(R.id.discount_percent);
        mSalePrice = findViewById(R.id.sale_price);
        mListPrice = findViewById(R.id.list_price);

        mDiscountPercentEditText = findViewById(R.id.discount_percent_edittext);
        mSalePriceEditText = findViewById(R.id.sale_price_edittext);
        mListPriceEditText = findViewById(R.id.list_price_edittext);

        mClearButton = findViewById(R.id.clear_button);
        mCalculateButton = findViewById(R.id.calculate_button);
        Spinner mSpinner = findViewById(R.id.spinner_app);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.array,android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(this);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        mCalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        clear();

        if(text.contains("List"))
        {
            spinnerSelected = 1;
            mListPrice.setVisibility(View.GONE);
            mListPriceEditText.setVisibility(View.GONE);

            mDiscountPercentEditText.setVisibility(View.VISIBLE);
            mDiscountPercent.setVisibility(View.VISIBLE);
            mSalePriceEditText.setVisibility(View.VISIBLE);
            mSalePrice.setVisibility(View.VISIBLE);

        }
        else if(text.contains("Discount"))
        {
            spinnerSelected = 2;

            mDiscountPercent.setVisibility(View.GONE);
            mDiscountPercentEditText.setVisibility(View.GONE);

            mSalePriceEditText.setVisibility(View.VISIBLE);
            mSalePrice.setVisibility(View.VISIBLE);
            mListPriceEditText.setVisibility(View.VISIBLE);
            mListPrice.setVisibility(View.VISIBLE);
        }
        else if(text.contains("Sale"))
        {
            spinnerSelected = 3;
            mSalePrice.setVisibility(View.GONE);
            mSalePriceEditText.setVisibility(View.GONE);

            mDiscountPercentEditText.setVisibility(View.VISIBLE);
            mDiscountPercent.setVisibility(View.VISIBLE);
            mListPriceEditText.setVisibility(View.VISIBLE);
            mListPrice.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void clear()
    {
        mDiscountPercentEditText.getText().clear();
        mSalePriceEditText.getText().clear();
        mListPriceEditText.getText().clear();
        mResultTextview.setVisibility(View.INVISIBLE);
        mResultTextview.setText("Your Results!");
    }
    public void calculate()
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mListPriceEditText.getWindowToken(), 0);

        mResultTextview.setVisibility(View.VISIBLE);
        switch (spinnerSelected)
        {
            case 1:

                if(!mDiscountPercentEditText.getText().toString().equals("") && !mSalePriceEditText.getText().toString().equals(""))
                {
                    String discountString = mDiscountPercentEditText.getText().toString();
                    double discountPercent = Double.parseDouble(discountString);

                    Log.d("discount", String.valueOf(discountPercent));

                    String salePriceString = mSalePriceEditText.getText().toString();
                    double salePrice = Double.parseDouble(salePriceString);

                    if((discountPercent >= 0 && discountPercent <= 100) && salePrice >= 0)
                    {
                        listPriceAmount = (100.0 * salePrice)/(100.0 - discountPercent);
                        discountAmount = (discountPercent/100.0) * listPriceAmount;

                        String resultString = String.format("List Price = $ %.2f \n\nDiscount Amount = $ %.2f",listPriceAmount, discountAmount);
                        mResultTextview.setText(resultString);
                    }
                    else
                    {
                        mResultTextview.setText("Enter Discount from 0% to 100%.");
                    }
                }
                break;

            case 2:

                if((!mListPriceEditText.getText().toString().equals("") && !mSalePriceEditText.getText().toString().equals(""))) {

                    listPriceAmount = Double.parseDouble(mListPriceEditText.getText().toString());

                    salePriceAmount = Double.parseDouble(mSalePriceEditText.getText().toString());
                    double differenceInPrices = listPriceAmount - salePriceAmount;

                    String resultString = "";
                    if(differenceInPrices >= 0)
                    {

                        discountPercentAmount = (differenceInPrices/listPriceAmount) * 100.0;
                        Log.d("difference", String.valueOf(discountPercentAmount));
                        discountAmount = listPriceAmount - salePriceAmount;

                        resultString = String.format("Discount Percent = %.2f %%\n \nDiscount Amount = $ %.2f",discountPercentAmount,discountAmount);
                    }
                    else
                    {
                        resultString = "Enter sale price less than list price.";
                    }
                    mResultTextview.setText(resultString);

                }
                break;

            case 3:

                if(!mListPriceEditText.getText().toString().equals("") && !mDiscountPercentEditText.getText().toString().equals(""))
                {
                    listPriceAmount = Double.parseDouble(mListPriceEditText.getText().toString());
                    discountPercentAmount = Double.parseDouble(mDiscountPercentEditText.getText().toString());

                    String resultString = "";

                    if(listPriceAmount > 0 && (discountPercentAmount >= 0 && discountPercentAmount <= 100))
                    {
                        salePriceAmount = (1.0 - (discountPercentAmount/100.0))*listPriceAmount;

                        discountAmount = ((discountPercentAmount)/100.0) * listPriceAmount;

                        resultString = String.format("Sale Price = $ %.2f \n \nDiscount Amount = $ %.2f",salePriceAmount,discountAmount);
                    }
                    else
                    {
                        resultString = "Enter Discount from 0% to 100%.";
                    }
                    mResultTextview.setText(resultString);
                }
                break;

            default:

        }

    }
}