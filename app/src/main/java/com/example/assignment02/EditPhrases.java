package com.example.assignment02;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.ArrayList;

public class EditPhrases extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView listView;
    Button save;
    Button edit;
    EditText editText;
    String oldPhrase;

    ArrayAdapter<String> adapter; //initialize an arrayAdapter
    ArrayList<String> data = new ArrayList<String>();

    private static final String TEXT_STATE = "currentText";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_phrases);
        listView = findViewById(R.id.listDisplay);
        save =  findViewById(R.id.saveBtn_edit);
        edit =  findViewById(R.id.editBtn_edit);
        editText =  findViewById(R.id.editText_edit);

        databaseHelper = new DatabaseHelper(this);
        displayAllData();
        // add all the phrases to the adapter by passing the arrayList
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,data);
        listView.setAdapter(adapter);
    }

    //display all the phrases
    public void displayAllData(){
        //get all the phrase
        Cursor cursor = databaseHelper.displayAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(EditPhrases.this,"No Phrases Available",Toast.LENGTH_LONG).show();
            return;
        }

        while (cursor.moveToNext()){
            //add the phrases to the arrayList
            data.add(cursor.getString(0) + "\n\n");
        }

    }

    public void openEdit(View view){

        try {
            //checked the position of the listView
            int position = listView.getCheckedItemPosition();
            //get the phrase in that position
            String input = listView.getItemAtPosition(listView.getCheckedItemPosition()).toString();
            //get only the letters in the string and avoid the white spaces
            String withoutAccent = Normalizer.normalize(input, Normalizer.Form.NFD);
            String selectedPhrase = withoutAccent.replaceAll("[^a-zA-Z ]", "");

            //get that input from the database
            Cursor cursor = databaseHelper.getPhraseName(input);

            if (position>-1) {
                while (cursor.moveToNext()) {
                    // updated editText with the phrase
                    editText.setText(cursor.getString(0));
                }
                //declare the phrase before edits
                oldPhrase = selectedPhrase;
                //updated editText with the old phrase
                editText.setText(oldPhrase);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Select a phrase before you edit",Toast.LENGTH_SHORT).show();
        }
    }

    //save or delete the selected phrase
    public void openSave(View view){
        String phrase = editText.getText().toString();
        //if user erase the selected phrase, it deleted from the database
        if (phrase.isEmpty()) {
            // remove the phrase
            databaseHelper.removeItem(oldPhrase);
            Toast.makeText(getBaseContext(), "Phrase deleted successfully", Toast.LENGTH_SHORT).show();
            //update the listView
            data.clear();
            displayAllData();
        } else {
            // if edited text already existed make a toast saying the text is existing
            boolean isExist = databaseHelper.isIDExist(phrase);
            if(isExist){
                Toast.makeText(EditPhrases.this, "Phrase exist", Toast.LENGTH_SHORT).show();

            }else {
                // if new text is entered, remove the old phrase and add the new phrase to the database
                databaseHelper.removeItem(oldPhrase);
                boolean isAdd = databaseHelper.addData(phrase);
                //update the listView
                data.clear();
                displayAllData();
                adapter.notifyDataSetChanged();
                if(isAdd ){
                    Toast t = Toast.makeText(EditPhrases.this, "Phrase has been updated successfully !", Toast.LENGTH_SHORT);
                    t.show();
                    editText.setText("");
                }else{
                    Toast.makeText(EditPhrases.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, editText.getText().toString());
    }
}