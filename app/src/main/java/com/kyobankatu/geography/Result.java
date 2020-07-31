package com.kyobankatu.geography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        final int score = intent.getIntExtra("score",0);
        final int num = intent.getIntExtra("num",0);

        ((TextView)findViewById(R.id.score)).setText((score+"/"+num));
    }
}