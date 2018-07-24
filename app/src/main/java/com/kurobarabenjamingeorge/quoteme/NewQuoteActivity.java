package com.kurobarabenjamingeorge.quoteme;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kurobarabenjamingeorge.quoteme.adapter.BackgroundImageAdapter;


public class NewQuoteActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, BackgroundImageAdapter.OnBackgroundImageItemClicked{

    private EditText newQuoteEditText;
    private TextView quoteTextView;

    private RecyclerView backgroundImagesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView quoteImage;

    private int[] images = {R.drawable.image_one, R.drawable.image_two, R.drawable.image_one, R.drawable.image_two,
            R.drawable.image_one, R.drawable.image_two,R.drawable.image_one, R.drawable.image_two};

    private String[] colours = {"red", "blue", "green", "orange", "yellow"};

    private void changeQuoteTextColour(String colour){
        Log.i("Selected colour:", colour);
        //Retrieves the ID for the selected colour
        int colourId = getResources().getIdentifier(colour, "color", getApplicationContext().getPackageName());
        Log.i("Colour id:", String.valueOf(colourId));
        int colourRes = ContextCompat.getColor(this, colourId);
        Log.i("Colour resource:", String.valueOf(colourRes));
        quoteTextView.setTextColor(colourRes);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);

        Spinner textColourSpinner = (Spinner) findViewById(R.id.textColourSpinner);

        newQuoteEditText = (EditText) findViewById(R.id.newQuoteEditText);
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);

        quoteImage = (ImageView) findViewById(R.id.quoteImage);

        backgroundImagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        backgroundImagesRecyclerView.setLayoutManager(layoutManager);
        BackgroundImageAdapter backgroundImageAdapter = new BackgroundImageAdapter(this, images, this);
        backgroundImagesRecyclerView.setAdapter(backgroundImageAdapter);

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
                quoteTextView.setText(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        changeQuoteTextColour(adapterView.getItemAtPosition(i).toString().toLowerCase());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        changeQuoteTextColour("red");
    }

    @Override
    public void onImageItemClicked(int imagePosition) {
        Toast.makeText(this, Integer.toString(imagePosition), Toast.LENGTH_SHORT).show();
        quoteImage.setImageResource(images[imagePosition]);
    }
}
