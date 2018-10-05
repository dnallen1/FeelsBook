package com.example.feelsbook;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// This class is a new Activity that displays all the different emotion counts
public class DisplayEmotionCount extends AppCompatActivity implements View.OnClickListener{

    TextView loveCount;
    TextView joyCount;
    TextView supriseCount;
    TextView angerCount;
    TextView sadnessCount;
    TextView fearCount;
    Button returnBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_count);

        returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(this);


        loveCount = findViewById(R.id.loveCountText);
        loveCount.setText(getEmotionCount("Love"));

        joyCount = findViewById(R.id.joyCountText);
        joyCount.setText(getEmotionCount("Joy"));

        supriseCount = findViewById(R.id.surpriseCountText);
        supriseCount.setText(getEmotionCount("Surprise"));

        angerCount = findViewById(R.id.angerCountText);
        angerCount.setText(getEmotionCount("Anger"));

        sadnessCount = findViewById(R.id.sadnessCountText);
        sadnessCount.setText(getEmotionCount("Sadness"));

        fearCount = findViewById(R.id.fearCountText);
        fearCount.setText(getEmotionCount("Fear"));

    }

    @Override
    public void onClick(View v) {
        finish();
    }


    public String getEmotionCount(String emotion){
        SharedPreferences pref = getSharedPreferences("myEmotionCount", MODE_PRIVATE);
        return pref.getString(emotion, null);
    }
}
