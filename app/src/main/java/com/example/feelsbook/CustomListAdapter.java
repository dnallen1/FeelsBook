package com.example.feelsbook;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/* Author: Nikhil Bansal
// Date: Juy 30, 2017
// Title: Custom Array Adapters made Easy!
// Source: https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd
   The general layout and all the compenents needed for a custom array adapter were
   used from Bansal's tutorial.
*/

public class CustomListAdapter extends ArrayAdapter {

    private Context context;
    private List<Comment> myDataSet;

    public CustomListAdapter(Activity context, List<Comment> myDataSet){
        super(context, R.layout.comment, myDataSet);
        this.context = context;
        this.myDataSet = myDataSet;

    }

    /* This method is to set the actaul Listview. There are three text boxes in for each item in
        the list. This method will put values into those three boxes
     */
    public View getView(int position, View view, ViewGroup parent){
       // creating object that will set the text color to match the appropriate emotion
        CommentTextColour commentTextColour = new CommentTextColour();

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView = inflater.inflate(R.layout.comment, null,true);

        TextView commentEmotion = rowView.findViewById(R.id.emotionText);
        TextView commentText = rowView.findViewById(R.id.commentText);
        TextView commentTime = rowView.findViewById(R.id.timeText);

        Comment comment = myDataSet.get(position); // retrieving the current emotion
        commentEmotion.setText(comment.getEmotion());
        commentEmotion.setTextColor(commentTextColour.setTextColor(context, comment.getEmotion().toString()));
        commentText.setText(comment.getComment());
        commentTime.setText(comment.getDate());

        return rowView;
    }


}
