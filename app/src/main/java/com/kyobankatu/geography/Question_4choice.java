package com.kyobankatu.geography;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class Question_4choice extends AppCompatActivity {

    private int score=0;
    private int num=0;
    private Iterator<JsonNode> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_4choice);

        final String path = getIntent().getStringExtra("PATH");

        try {
            assert path != null;

            ObjectMapper om = new ObjectMapper();
            questions = shuffleIterator(om.readTree(new File(path)).get("questions").iterator());

            nextQuestion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void nextQuestion(){
        if(questions.hasNext()){
            JsonNode question = questions.next();

            ((TextView)findViewById(R.id.question)).setText(question.get("question").asText());

            Iterator<JsonNode> choices = shuffleIterator(((ArrayNode)question.get("choice")).iterator());

            LinearLayout ans = (LinearLayout)findViewById(R.id.ans);
            IntStream.range(0,ans.getChildCount())
                    .mapToObj(ans::getChildAt)
                    .map(obj -> (Button)obj)
                    .forEach(b -> {
                        String text = choices.next().asText();
                        b.setText(text);
                        b.setTag(text.equals(question.get("ans").asText()));
                    });
        }else{
            Intent intent = new Intent(getApplicationContext(), Result.class);
            intent.putExtra("score",score);
            intent.putExtra("num",num);
            startActivity(intent);
        }
    }

    private <T> Iterator<T> shuffleIterator(Iterator<T> iterator){
        ArrayList<T> arrayList = new ArrayList<>();
        iterator.forEachRemaining(arrayList::add);
        Collections.shuffle(arrayList);
        return arrayList.iterator();
    }

    public void onButtonClick(View view){
        num++;
        if((boolean)view.getTag()){
            score++;
        }
        nextQuestion();
        System.out.println(score);
    }
}