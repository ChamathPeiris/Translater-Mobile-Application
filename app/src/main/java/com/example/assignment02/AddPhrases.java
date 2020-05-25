package com.example.assignment02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPhrases extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    EditText phraseId;
    Button saveBtn;

    private static final String TEXT_STATE = "currentText";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_phrases);

        databaseHelper = new DatabaseHelper(this);
        phraseId = findViewById(R.id.add_editText); //declare the editText
        saveBtn = findViewById(R.id.addSave_btn); // declare the button
    }

    // add data to the sqLite database
    public void addData(View view) {

        String phrase = phraseId.getText().toString();

        if (phrase.isEmpty()) {
            // if edit text is empty, mke toast message
            Toast.makeText(getBaseContext(), "Please enter a Phrase", Toast.LENGTH_SHORT).show();
        } else {
            // if the entered phrase already existed, make toast saying it existed
            boolean isExist = false;
            try {
                isExist = databaseHelper.isIDExist(phraseId.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(isExist){
                Toast.makeText(AddPhrases.this, "Phrase exist", Toast.LENGTH_SHORT).show();

            }else {
                // if the entered phrase doesn't exist, add the phrase to the database
                boolean isAdd = databaseHelper.addData(phraseId.getText().toString());
                if(isAdd){
                    Toast t = Toast.makeText(AddPhrases.this, "Phrase has been added successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    phraseId.setText("");
                }else{
                    Toast.makeText(AddPhrases.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, phraseId.getText().toString());
    }
}
