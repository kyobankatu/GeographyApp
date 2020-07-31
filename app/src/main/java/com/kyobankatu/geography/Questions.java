package com.kyobankatu.geography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Questions extends AppCompatActivity {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        final LinearLayout layout = findViewById(R.id.scroll_LinerLayout);

        HashMap<Button, Date> buttons = new HashMap<>();

        List<File> fileList = getJsonFiles();
        ObjectMapper om = new ObjectMapper();
        try {
            if(fileList == null) throw new Exception("There are no json files.");

            for(File file:fileList){
                JsonNode node = om.readTree(file);
                buttons.put(createButton(node.get("name").asText(), file.getPath(), node.get("type").asText()), sdf.parse(node.get("date").asText()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayList<Map.Entry<Button,Date>> sorted_buttons = new ArrayList<>(buttons.entrySet());
        Collections.sort(sorted_buttons, (t, t1) -> t.getValue().compareTo(t1.getValue()));

        sorted_buttons.forEach(i -> layout.addView(i.getKey()));
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

    private Button createButton(String text, String path, String type){
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(20);
        button.setTextColor(Color.parseColor("#4D4234"));

        switch (type) {
            case "4choice":
                button.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), Question_4choice.class);
                    intent.putExtra("PATH",path);
                    startActivity(intent);
                });
                break;
            default:
                throw new IllegalArgumentException("It is IllegalType.");
        }
        return button;
    }
}