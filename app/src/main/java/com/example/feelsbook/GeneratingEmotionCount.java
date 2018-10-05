package com.example.feelsbook;

import android.content.Context;
import android.content.SharedPreferences;


import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

// This class counts the number of emotions in the data set
public class GeneratingEmotionCount {

    List<String> fullEmotionList = Arrays.asList("Love", "Joy", "Surprise", "Anger", "Sadness", "Fear");

    public String emotionCount (String emotion, List<Comment> myDataSet){
        int count = 0;
        for (Comment comment : myDataSet) {
            if (comment.getEmotion().equals(emotion)){
                ++count;
            }
        }
        return String.valueOf(count);

    }

    public void addingEmotionCount (Context context, List<Comment> myDataSet){
        SharedPreferences emotionArray = context.getSharedPreferences("myEmotionCount", MODE_PRIVATE);
        SharedPreferences.Editor edit = emotionArray.edit();
        for (String emotion : fullEmotionList){
            edit.remove(emotion);
            edit.putString(emotion, emotionCount(emotion, myDataSet));
        }
        edit.commit();
    }
}
