package com.example.feelsbook;

import android.content.Context;
import android.support.v4.content.ContextCompat;

// This class sets the text colour of the emotion text
public class CommentTextColour {
    int emotionColor;

    /* This method changes the text colour of the emotions in the history list
        Inputs: context - the context from the CustomListAdapter
                emotion - the emotion chosen
        Outputs: the colour of the emotion
     */
    public Integer setTextColor(Context context, String emotion){
        switch (emotion) {
            case "Love":
                /* Author: xbakesx
                // Date: Dec 7, 2015
                // Title: How to set the text color of TextView in code?
                // Source: https://stackoverflow.com/questions/4602902/how-to-set-the-text-color-of-textview-in-code
                */
                emotionColor = ContextCompat.getColor(context, R.color.loveColor);
                break;
            case "Joy":
                emotionColor = ContextCompat.getColor(context, R.color.joyColor);
                break;
            case "Surprise":
                emotionColor = ContextCompat.getColor(context, R.color.surpriseColor);
                break;
            case "Anger":
                emotionColor = ContextCompat.getColor(context, R.color.angerColor);
                break;
            case "Sadness":
                emotionColor = ContextCompat.getColor(context, R.color.sadnessColor);
                break;
            case "Fear":
                emotionColor = ContextCompat.getColor(context, R.color.fearColor);
                break;
        }

        return emotionColor;
    }
}
