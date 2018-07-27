package com.kurobarabenjamingeorge.quoteme;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.view.MotionEvent;
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

    private static final int GALLERY_REQUEST = 1;

    float dX, dY, startTextX, startTextY;

    private void changeQuoteTextColour(String colour){
        //Retrieves the ID for the selected colour
        int colourId = getResources().getIdentifier(colour, "color", getApplicationContext().getPackageName());
        int colourRes = ContextCompat.getColor(this, colourId);
        quoteTextView.setTextColor(colourRes);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quote);

        Spinner textColourSpinner = (Spinner) findViewById(R.id.textColourSpinner);

        newQuoteEditText = (EditText) findViewById(R.id.newQuoteEditText);
        quoteTextView = (TextView) findViewById(R.id.quoteTextView);

        startTextX = quoteTextView.getX();
        startTextY = quoteTextView.getY();

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

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        quoteTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float lastAction;
                switch(motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        //Toast.makeText(NewQuoteActivity.this, "Action down", Toast.LENGTH_SHORT).show();
                        dX = view.getX() - motionEvent.getRawX();
                        dY = view.getY() - motionEvent.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Toast.makeText(NewQuoteActivity.this, "Moving", Toast.LENGTH_SHORT).show();
                        view.setY(motionEvent.getRawY() + dY);
                        view.setX(motionEvent.getRawX() + dX);
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    case MotionEvent.ACTION_UP:
                        float targetX = quoteImage.getX();
                        float targetY =quoteImage.getY();
                        float textX = quoteTextView.getX();
                        float textY = quoteTextView.getY();
                        float textWidth = quoteTextView.getWidth();
                        float textHeight = quoteTextView.getHeight();
                        float targetWidth = quoteImage.getWidth();
                        float targetHeight = quoteImage.getHeight();

                        Log.i("text width", String.valueOf(textWidth));

                        if(textX > targetX && textX+textWidth < targetX+targetWidth){
                            if(textY > targetY && textY + textHeight < targetY + targetHeight){
                                Toast.makeText(NewQuoteActivity.this, "Within target", Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(NewQuoteActivity.this, "Off target", Toast.LENGTH_SHORT).show();
                            quoteTextView.setX(startTextX);
                            quoteTextView.setY(startTextY);
                        }
                        //Toast.makeText(NewQuoteActivity.this, "Action Up", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return false;
                }
                return true;
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
            case android.R.id.home:
                Toast.makeText(this, "Up button pressed", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveQuoteImage() {

        if(quoteTextView.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Cannot save an empty quote", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder saveQuoteAlertBuilder = new AlertDialog.Builder(NewQuoteActivity.this);

        View saveQuoteCustomView = getLayoutInflater().inflate(R.layout.save_dialog_layout, null);
        final EditText quoteNameEditText = saveQuoteCustomView.findViewById(R.id.quoteNameEditText);
        Button saveQuoteButton = (Button) saveQuoteCustomView.findViewById(R.id.save_quote_button);
        Button cancelDialogButton = (Button) saveQuoteCustomView.findViewById(R.id.cancel_dialog_button);

        saveQuoteAlertBuilder.setView(saveQuoteCustomView);

        final AlertDialog saveQuoteDialog = saveQuoteAlertBuilder.create();
        saveQuoteDialog.show();

        saveQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!quoteNameEditText.getText().toString().trim().isEmpty()){

                    String nameOfQuote = quoteNameEditText.getText().toString().trim();

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

                        f = new File(file.getAbsolutePath()+ "/" + nameOfQuote+".png");
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
                    saveQuoteDialog.dismiss();

                }else{
                    Toast.makeText(NewQuoteActivity.this, R.string.save_quote_warning, Toast.LENGTH_SHORT).show();

                }
            }
        });

        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuoteDialog.dismiss();

            }
        });




    }

    public void pickFromGallery(View view) {
        //Toast.makeText(this, "Picking from gallery", Toast.LENGTH_SHORT).show();
        String mimeType = "image/*";
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType(mimeType);
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  GALLERY_REQUEST && resultCode == RESULT_OK && data!= null && data.getData() != null){
            Uri imageUri  = data.getData();

            try{
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                quoteImage.setImageBitmap(imageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        showDiscardQuoteConfirmDialog();
    }

    private void showDiscardQuoteConfirmDialog(){
        AlertDialog.Builder confirmDialogBuilder = new AlertDialog.Builder(NewQuoteActivity.this);
        confirmDialogBuilder.setTitle(R.string.discard_quote);
        confirmDialogBuilder.setMessage(R.string.discard_quote_dialog_message);

        confirmDialogBuilder.setPositiveButton("DISCARD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        confirmDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog discardQuoteConfirmDialog = confirmDialogBuilder.create();

        discardQuoteConfirmDialog.show();

    }
}
