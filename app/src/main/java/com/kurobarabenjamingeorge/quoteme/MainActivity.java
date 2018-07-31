package com.kurobarabenjamingeorge.quoteme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_IMG_REQ = 10;
    public static final String SELECTED_PHOTO_EXTRA = "com.kurobarabenjamingeorge.quoteme.SELECTED_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadNewQuoteActivity(View view) {
        View loadQuoteImageDialogView = LayoutInflater.from(this).inflate(R.layout.layout_load_quote_image_dialog, null);

        AlertDialog.Builder loadQuoteImageDialogBuilder = new AlertDialog.Builder(this);
        loadQuoteImageDialogBuilder.setView(loadQuoteImageDialogView);
        final ImageView galleryImageView = (ImageView) loadQuoteImageDialogView.findViewById(R.id.galleryImageView);
        ImageView appBackgroundImageView = (ImageView)loadQuoteImageDialogView.findViewById(R.id.appBackgroundsImageView);

        galleryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_IMG_REQ);
            }
        });

        appBackgroundImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Okay dokey", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog loadQuoteImageDialog = loadQuoteImageDialogBuilder.create();
        loadQuoteImageDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_IMG_REQ && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imageUri = data.getData();
            String stringUri = imageUri.toString();
            Toast.makeText(this, imageUri.toString(), Toast.LENGTH_SHORT).show();
            Intent newQuoteIntent = new Intent(MainActivity.this, NewQuoteActivity.class);
            newQuoteIntent.putExtra(SELECTED_PHOTO_EXTRA, stringUri);
            startActivity(newQuoteIntent);

        }
    }
}
