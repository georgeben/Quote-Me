package com.kurobarabenjamingeorge.quoteme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class NewQuoteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);

        Spinner textColourSpinner = (Spinner) findViewById(R.id.textColourSpinner);

        ArrayAdapter<CharSequence> textColourSpinnerAdapter = ArrayAdapter.createFromResource(NewQuoteActivity.this,
                R.array.text_colour_options, android.R.layout.simple_spinner_item);

        textColourSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        textColourSpinner.setAdapter(textColourSpinnerAdapter);

        textColourSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
