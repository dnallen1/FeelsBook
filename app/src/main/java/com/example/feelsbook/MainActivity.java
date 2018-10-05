package com.example.feelsbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
  The MainActivity class deals with the functioning of all the emotions. It sets all the buttons
  to listen for clicks and directs the flow of the app. The saving and loading from the
  file also is done in this class.
 */

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //Initializing all buttons on main screen
    Button fearBtn;
    Button loveBtn;
    Button joyBtn;
    Button surpriseBtn;
    Button angerBtn;
    Button sadnessBtn;
    EditText addCommentText;

    //Initializing my list view with emotions and the adapter
    ListView listView;
    CustomListAdapter adapter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<Comment> myDataSet = new ArrayList<Comment>();
    List<String> fullEmotionList = Arrays.asList("Love", "Joy", "Surprise", "Anger", "Sadness", "Fear");

    String data;
    Integer selectedPosition;

    /* This method creates the menu options (Emotion Count tab)
       Inputs: menu - this is a resource file that has a layout of the menu in it
       Outputs: true or false
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // being able to use the menu at the top of the app
        getMenuInflater().inflate(R.menu.countmenu, menu);
        return true;
    }

    /* This method deals with when the user clicks on the Emotion Count tab on the main menu
       Inputs: item - this is the menu item clicked
       Outputs: true or false
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.countMenu) {
            GeneratingEmotionCount generatingEmotionCount = new GeneratingEmotionCount();
            generatingEmotionCount.addingEmotionCount(this, myDataSet);
            Intent intent = new Intent(MainActivity.this, DisplayEmotionCount.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /* Author: joshua2ua
       Title: Lonely Twitter demo for CSCI 301
       Source: https://github.com/joshua2ua/lonelyTwitter
       The general flow for the main screen closey follows the Lonely Twitter app, as well
       as creating the adapter for the ListView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This line is adjusting the app for when the keyboard pops up
        /* Author: Bobs
         Date: Feb 25, 2013
         Title: Move layouts up when soft keyboard is shown?
         Source: https://stackoverflow.com/questions/1964789/move-layouts-up-when-soft-keyboard-is-shown
        */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        addCommentText = findViewById(R.id.commentTextView);
        addCommentText.setOnClickListener(this);


        fearBtn = findViewById(R.id.fearBtn);
        fearBtn.setOnClickListener(this);
        loveBtn = findViewById(R.id.loveBtn);
        loveBtn.setOnClickListener(this);
        joyBtn = findViewById(R.id.joyBtn);
        joyBtn.setOnClickListener(this);
        surpriseBtn = findViewById(R.id.surpriseBtn);
        surpriseBtn.setOnClickListener(this);
        angerBtn = findViewById(R.id.angerBtn);
        angerBtn.setOnClickListener(this);
        sadnessBtn = findViewById(R.id.sadnessBtn);
        sadnessBtn.setOnClickListener(this);


        final CustomListAdapter adapter = new CustomListAdapter(this, myDataSet);
        listView = findViewById(R.id.scrollingList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position; // getting the current emotion that is selected

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Edit or Delete");

                //Set edit Button
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Comment comment = myDataSet.get(selectedPosition);
                        SharedPreferences pref = getSharedPreferences("key", MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("Comment", comment.getComment());
                        edit.putString("Time", comment.getDate());
                        edit.commit();
                        //creating an intent which will go to the new activity
                        Intent intent = new Intent(MainActivity.this, EditCommentActivity.class);
                        startActivityForResult(intent, 0);

                    }
                });
                // set delete button
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteComment();
                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile();
        adapter = new CustomListAdapter(this, myDataSet);
        listView = findViewById(R.id.scrollingList);
        listView.setAdapter(adapter);
    }

    /* This method adds an emotion whenever the user clicks on an emotion
       Inputs: selected Emotion view (View)
       Outputs: None
     */

    @Override
    public void onClick(View v) {
        Button buttonClicked = (Button) v;
        addComment(buttonClicked.getText().toString());
    }
    /* This method deals with returning to the main activiy (starting activity) when the user
        returns from the EditCommentActivity (second activity)
       Inputs: requestcode - this is the value that tells you which activity was the starting activity
               resultCode - this is the value that tells you which activity you are returning with
               data - this is the "data" passed from the second activity

       Outputs: None
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                String newComment = data.getStringExtra("comment");
                String newTime = data.getStringExtra("time");
                addEditComment(newComment, newTime);
                createToast("Emotion Edited");
            } else if (resultCode == RESULT_CANCELED) {}
    }


    // This method deletes an emotion from the data set and updates the listview
    public void deleteComment(){
        Comment comment = myDataSet.get(selectedPosition);
        myDataSet.remove(comment);
        adapter.notifyDataSetChanged();
        saveInFile();
    }

    /* This method adds an emotion from the data set and updates the listview
        Inputs: emotion - the emotion you clicked on (ie: Fear, Love...)
     */
    public void addComment(final String emotion){
        Date date = new Date();
        myDataSet.add(new Comment(emotion, addCommentText.getText().toString(), dateFormat.format(date)));
        sortListView();
        adapter.notifyDataSetChanged();
        createToast("Emotion Added");
        saveInFile();
    }

    /* this method replaces the old emotion with the edited version, then resorts the list,
     then updates the list view
     Inputs: commentText - this is the edited text
             timeText - this is the edited date
     */
    public void addEditComment(String commentText, String timeText){
        Comment comment = myDataSet.get(selectedPosition);
        myDataSet.set(selectedPosition, new Comment(comment.getEmotion(), commentText, timeText));
        sortListView();
        adapter.notifyDataSetChanged();
        createToast("Emotion Edited");
        saveInFile();

    }

    // This method sorts the dataset full of different emotions, then reverses it so the
    // listview shows the newest at the top
    public void sortListView(){
        Collections.sort(myDataSet);
        Collections.reverse(myDataSet);
    }

    /* This method creates a toast to be displayed when an emotion is added or edited
        Inputs: text - this is the text to be displayed on the toast
     */
    public void createToast(String text){
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
    }

    /* Author: joshua2ua
       Title: Lonely Twitter demo for CSCI 301
       Source: https://github.com/joshua2ua/lonelyTwitter
       The loadFromFile() and saveInFile() were taken directly from the Lonely Twitter app, only
       changed to include myDataSet
 */
    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput("FeelsBook.sav");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            Gson gson = new Gson();
            Type myDataSetType = new TypeToken<ArrayList<Comment>>(){}.getType();
            myDataSet = gson.fromJson(reader, myDataSetType);
        } catch (FileNotFoundException e){
            myDataSet = new ArrayList<Comment>();
        }
    }

    private void saveInFile() {
        try{
            FileOutputStream fos = openFileOutput("FeelsBook.sav", 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            gson.toJson(myDataSet, writer);
            writer.flush();
            fos.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }


}
