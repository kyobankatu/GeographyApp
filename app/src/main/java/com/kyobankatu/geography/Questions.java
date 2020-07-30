package com.kyobankatu.geography;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Questions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        final LinearLayout layout = findViewById(R.id.scroll_LinerLayout);

        ArrayList<Button> buttons = new ArrayList<>();

        List<File> fileList = getJsonFiles();
        ObjectMapper om = new ObjectMapper();
        try {
            if(fileList == null) throw new Exception("There are no json files.");

            for(File file:fileList){
                JsonNode node = om.readTree(file);
                buttons.add(createButton(node.get("name").asText()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        buttons.forEach(layout::addView);
    }

    private List<File> getJsonFiles() {
        File dir = new File(getApplicationContext().getFilesDir()+"/data");
        File[] files = dir.listFiles();
        if(files==null) {
            return null;
        }else {
            return Arrays.asList(files);
        }
    }

    private Button createButton(String text){
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(20);
        button.setTextColor(Color.parseColor("#4D4234"));
        return button;
    }
}