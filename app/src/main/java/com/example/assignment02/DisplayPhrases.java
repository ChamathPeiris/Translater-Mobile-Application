package com.example.assignment02;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayPhrases extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    TextView display;

    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_phrases);

        databaseHelper = new DatabaseHelper(this);
        displayAllData();
        // Restore TextView if there is a savedInstanceState
        if(savedInstanceState!=null){
            display.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    //display phrase from the sqLite database
    public void displayAllData(){
        Cursor cursor = databaseHelper.displayAllData();
        //if non of the phrases in the database make a toast
        if (cursor.getCount() == 0){
            Toast.makeText(DisplayPhrases.this,"No Phrases Available",Toast.LENGTH_LONG).show();
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()){
            // add one by one to the end of the existence text
            stringBuffer.append(cursor.getString(0) + "\n\n");

        }

        display = findViewById(R.id.display_textView); // declare the textView
        display.setText(stringBuffer.toString()); // display the phrases
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, display.getText().toString());
    }
}
