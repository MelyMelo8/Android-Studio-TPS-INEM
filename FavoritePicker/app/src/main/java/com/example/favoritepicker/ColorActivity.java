package com.example.favoritepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
    }

    public void returnedRed(View view){
        returnedFeedBack("RED");
    }

    public void returnedGreen(View view){
        returnedFeedBack("GREEN");
    }

    public void returnedOrange(View view){
        returnedFeedBack("ORANGE");
    }

    public void returnedBlue(View view){
        returnedFeedBack("BLUE");
    }

    public void returnedReset(View view){
        returnedFeedBack("WHITE");
    }

    private void returnedFeedBack(String color){
        Intent colorFeedBack = new Intent();
        colorFeedBack.putExtra("color", color);
        setResult(RESULT_OK, colorFeedBack);
        finish();
    }
}