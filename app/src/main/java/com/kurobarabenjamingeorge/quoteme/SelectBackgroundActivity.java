package com.kurobarabenjamingeorge.quoteme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.kurobarabenjamingeorge.quoteme.adapter.BackgroundImageAdapter;

public class SelectBackgroundActivity extends AppCompatActivity 
        implements  BackgroundImageAdapter.OnBackgroundImageItemClicked{
    
    private RecyclerView mRecyclerView;
    private BackgroundImageAdapter adapter;

    public static final String SELECTED_BG = "com.kurobarabenjamingeorge.quoteme.SELECTED_BACKGROUND";

    private int[] images = {R.drawable.image_one, R.drawable.image_two, R.drawable.image_one, R.drawable.image_two,
            R.drawable.image_one, R.drawable.image_two,R.drawable.image_one, R.drawable.image_two};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_background);
        
        int numberOfColumns = 3;
        
        mRecyclerView = (RecyclerView) findViewById(R.id.selectBackgroundRV);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new BackgroundImageAdapter(this, images, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onImageItemClicked(int imagePosition) {
        //Toast.makeText(this, "An image has been tapped", Toast.LENGTH_SHORT).show();
        Intent newQuoteIntent = new Intent(SelectBackgroundActivity.this, NewQuoteActivity.class);
        newQuoteIntent.putExtra(SELECTED_BG, images[imagePosition]);
        startActivity(newQuoteIntent);
    }
}
