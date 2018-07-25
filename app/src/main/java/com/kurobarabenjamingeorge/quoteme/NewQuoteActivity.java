package com.kurobarabenjamingeorge.quoteme;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kurobarabenjamingeorge.quoteme.adapter.BackgroundImageAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class NewQuoteActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, BackgroundImageAdapter.OnBackgroundImageItemClicked{

    private EditText newQuoteEditText;
    private TextView quoteTextView;

    private RecyclerView backgroundImagesRecyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView quoteImage;
    private RelativeLayout quoteLayout;

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
        quoteLayout = (RelativeLayout) findViewById(R.id.quoteLayout);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_quote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_save_quote:
                //Toast.makeText(this, "Saving quote", Toast.LENGTH_SHORT).show();
                saveQuoteImage();
                return true;
            case R.id.action_share:
                Toast.makeText(this, "Sharing quote", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_cancel:
                Toast.makeText(this, "Cancelling quote", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveQuoteImage() {
        quoteLayout.setDrawingCacheEnabled(true);
        Bitmap b = quoteLayout.getDrawingCache();
        Bitmap quoteBitmap = b.copy(Bitmap.Config.ARGB_8888, false);
        File file,f;
        f = null;

        if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            file = new File(android.os.Environment.getExternalStorageDirectory(), "Quote Me");
            if(!file.exists()){
                file.mkdirs();
            }

            f = new File(file.getAbsolutePath()+ "/filename"+".png");
        }

        try{
            FileOutputStream ostream = new FileOutputStream(f);
            quoteBitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();

            Toast.makeText(NewQuoteActivity.this, "Save successful", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
