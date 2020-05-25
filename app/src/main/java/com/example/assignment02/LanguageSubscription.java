package com.example.assignment02;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguage;
import com.ibm.watson.language_translator.v3.model.IdentifiableLanguages;

import java.util.ArrayList;
import java.util.List;

public class LanguageSubscription extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ListView languageList;
    Button languageUpdateBtn;

    ArrayAdapter<String> adapter; //initialize an arrayAdapter
    ArrayList<String> data = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language);
        databaseHelper = new DatabaseHelper(this);
        languageList = findViewById(R.id.languageList);
        languageUpdateBtn = findViewById(R.id.language_updateBtn);

        Cursor cursor = databaseHelper.displayAllLanguages();

        //execute the AsyncTask only cursor is empty
        if (cursor.getCount() == 0){
            Languages process = new Languages();
            process.execute();
            displayAllData();
        }else {
            displayAllData();
        }
        // add all the phrases to the adapter by passing the arrayList
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,data);
        languageList.setAdapter(adapter);

    }

    private class Languages extends AsyncTask<Void,Void, List<IdentifiableLanguage>> {
        private List<IdentifiableLanguage> getLanguages; //declare a list
        @Override
        //this execute the work that is to be performed on the separate thread.
        //get the languages from the ibm clod service
        //https://cloud.ibm.com/apidocs/language-translator/language-translator?code=java#list-identifiable-languages
        protected List<IdentifiableLanguage> doInBackground(Void... voids) {
            IamAuthenticator authenticator = new IamAuthenticator("8KC7BUfkX6qx72WMOewrFATjDLVkrqvjDY9-_zLIPaXH");
            LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
            languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/74587b5c-a402-4e70-af43-f7ade3a53c8e");
            IdentifiableLanguages languages = languageTranslator.listIdentifiableLanguages().execute().getResult();
            System.out.println(languages);
            getLanguages = languages.getLanguages();
            return getLanguages;
        }

        // updating the results to the UI once the AsyncTask has finished loading
        @Override
        protected void onPostExecute(List<IdentifiableLanguage> listLanguages) {
            super.onPostExecute(listLanguages);
            for(IdentifiableLanguage selectedLanguage: listLanguages){
                String code = selectedLanguage.getLanguage();
                String name = selectedLanguage.getName();
                //add languages and their codes to the sqLite database
                databaseHelper.addLanguage(name,code,0);
            }
        }
    }

    //display languages
    public void displayAllData(){
        Cursor cursor = databaseHelper.displayAllLanguages();
        if (cursor.getCount() == 0){
            Toast t = Toast.makeText(LanguageSubscription.this, "Not any languages available", Toast.LENGTH_SHORT);
            t.show();
            return;
        }
        while (cursor.moveToNext()){
            // add languages to the "data" arrayList
            data.add(cursor.getString(0) );
        }
    }

//    https://stackoverflow.com/questions/16786581/get-value-multiple-choice-listview-android/16795742
    public void openUpdate(View view){

        int length = languageList.getCount();
        //in the sparsBooleanArray map integer length to booleans
        SparseBooleanArray sparseBooleanArray = languageList.getCheckedItemPositions();
        //check for selected languages and change their "isChecked" to "1" .
        for(int i = 0; i < length; i++){
            if(sparseBooleanArray.get(i)) {
                String language = (String) languageList.getItemAtPosition(i);

                databaseHelper.UpdateCheck(1,language);
                Toast t = Toast.makeText(LanguageSubscription.this, "Subscribed Successfully", Toast.LENGTH_SHORT);
                t.show();
                adapter.notifyDataSetChanged();

            }

            }

        }

    }




