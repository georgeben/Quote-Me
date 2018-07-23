package com.kurobarabenjamingeorge.quoteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewQuoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText newQuoteEditText;
    private TextView quoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);

        Spinner textColourSpinner = (Spinner) findViewById(R.id.textColourSpinner);

        newQuoteEditText = (EditText) findViewById(R.id.newQuoteEditText);
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);

        ArrayAdapter<CharSequence> textColourSpinnerAdapter = ArrayAdapter.createFromResource(NewQuoteActivity.this,
                R.array.text_colour_options, android.R.layout.simple_spinner_item);

        textColourSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        textColourSpinner.setAdapter(textColourSpinnerAdapter);

        textColourSpinner.setOnItemSelectedListener(this);

        //Detecting when the user has started typing
        newQuoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Toast.makeText(NewQuoteActivity.this, String.valueOf(charSequence), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
