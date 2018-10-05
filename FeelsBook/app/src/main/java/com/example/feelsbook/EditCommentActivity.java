package com.example.feelsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
    This class is an Activity that deals with Editing a comment. The user will select a comment
    and the app will navigate to this page. The user will put in their edits, and when they
    hit "Enter" the app will return to the Main Activity and this current activity will
    be destroyed.

 */
public class EditCommentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editComment;
    EditText editTime;
    Button enterBtn;
    Button cancelBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);

        editComment = findViewById(R.id.editComment);
        editTime = findViewById(R.id.editTime);
        enterBtn = findViewById(R.id.editBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        enterBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        //Displaying the Emotion you want to edit in the TextViews
        SharedPreferences pref = getSharedPreferences("key", MODE_PRIVATE);
        String oldComment = pref.getString("Comment", null); // getting comment from previous activity
        String oldTime = pref.getString("Time", null); // getting date from previous activity
        editComment.setText(oldComment);
        editTime.setText(oldTime);
    }

    @Override
    public void onClick(View v) {
        if (v == enterBtn){
            String newComment = editComment.getText().toString();
            String newTime = editTime.getText().toString();
            Intent intent = new Intent(); //creating a new intent will pass the edited Emotion back to the main activity
            intent.putExtra("comment", newComment);
            intent.putExtra("time", newTime);
            setResult(MainActivity.RESULT_OK, intent);
            finish();
        } else if (v == cancelBtn){
            Intent intent = new Intent();
            setResult(MainActivity.RESULT_CANCELED, intent);
            finish();
        }
    }
}
