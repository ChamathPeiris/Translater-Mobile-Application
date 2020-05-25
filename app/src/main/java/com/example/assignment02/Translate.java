package com.example.assignment02;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ibm.cloud.sdk.core.http.HttpMediaType;
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;

import java.util.ArrayList;
import static android.content.ContentValues.TAG;
public class Translate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper databaseHelper;
    String spinner;
    Spinner languageDropdown;
    ListView listView;
    Button translateBtn;
    Button pronounceBtn;
    TextView textView;
    private LanguageTranslator translationService;
    String languageCode;
    String selectedPhrase;
    private StreamPlayer player = new StreamPlayer();
    private TextToSpeech textService;

    boolean translateAllBtnChecked = false;

    ArrayAdapter<String> adapter; //initialize an arrayAdapter
    ArrayList<String> data = new ArrayList<String>();

    private static final String TEXT_STATE = "currentText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translate);
        databaseHelper = new DatabaseHelper(this);
         listView= findViewById(R.id.listView_translate);
        translateBtn = findViewById(R.id.translateBtn_translate);
        pronounceBtn = findViewById(R.id.pronounceBtn_translate);
        textView = findViewById(R.id.textView_translate);
        displayAllData();
        displayDropdown();

        textView = findViewById(R.id.textView_translate);

        // add all the phrases to the adapter by passing the arrayList
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,data);
        listView.setAdapter(adapter);
        textService = initTextToSpeechService(); //call the textToSpeech method

        //change the background colour of selected position in listView
        //https://www.tutorialspoint.com/how-to-change-the-background-color-of-listview-items-on-android
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick (AdapterView<?> parent , View view , int position , long id) {
                view.setBackgroundColor(getColor(R.color. colorPrimaryDark )) ;
            }
        }) ;
        // Restore TextView if there is a savedInstanceState
        if(savedInstanceState!=null){
            textView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    //display all languages
    public void displayAllData(){
        Cursor cursor = databaseHelper.displayAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(Translate.this,"There are no Phrases here",Toast.LENGTH_SHORT).show();
            return;
        }

        // add to the arrayList
        while (cursor.moveToNext()){
            data.add(cursor.getString(0) + "\n");
        }

    }

    public void displayDropdown(){
        ArrayList<String> subscribedLanguages = new ArrayList<>();
        Cursor cursor = databaseHelper.getSubscribedLanguages();
        while (cursor.moveToNext()){
            subscribedLanguages.add(cursor.getString(0));
        }
        // Initialize the spinner.
        languageDropdown = findViewById(R.id.spinner_translate);

        if (languageDropdown != null) {
            //if select an item from the spinner then run the method
            languageDropdown.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout and Specify the layout to use when the list of choices appears.
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,subscribedLanguages);

        // Specify the layout to use when the list of choices appears.
        // Apply the adapter to the spinner.
        if (languageDropdown != null) {
            languageDropdown.setAdapter((SpinnerAdapter) adapter);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner = parent.getItemAtPosition(position).toString(); // invoked when an item in this view has been selected
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //Apply the translation service
    private LanguageTranslator initLanguageTranslatorService() {
        //https://cloud.ibm.com/apidocs/language-translator/language-translator?code=java#list-identifiable-languages
        IamAuthenticator authenticator  = new IamAuthenticator("8KC7BUfkX6qx72WMOewrFATjDLVkrqvjDY9-_zLIPaXH");
        LanguageTranslator service = new LanguageTranslator("2018-05-01", authenticator);
        service.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/74587b5c-a402-4e70-af43-f7ade3a53c8e");
        return service;
    }

    public class TranslationTask extends AsyncTask<String,Void,String> {
        @Override
        //this execute the work that is to be performed on the separate thread.
        protected String doInBackground(String... params) {
            TranslateOptions translateOptions = new TranslateOptions.Builder()
                    .addText(params[0])
                    .source(Language.ENGLISH) //declare the given language
                    .target(languageCode) //pass the code of the selected language
                    .build();

            String firstTranslation = null;
            try {
                TranslationResult result= translationService.translate(translateOptions).execute().getResult();
                firstTranslation = result.getTranslations().get(0).getTranslation();
            } catch (RuntimeException e) {
                e.printStackTrace();
                //make a toast if the selected language not found
                //we can't make a toast in in background running thread so create uiThread
                runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(getApplicationContext(), "Language Not Found",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
            return firstTranslation;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s); // set the translated word to the text
            //only the translateAll button pressed, translation go to the database
            if(translateAllBtnChecked){
                //add the translated phrase to the database
                databaseHelper.addTranslationData(languageCode,s);
            }

        }
    }

    //get the selected language code
    public void getLanguageCode(){
        Cursor cursor = databaseHelper.getSelectedLanguageCode(spinner);
        while (cursor.moveToNext()){
            languageCode = cursor.getString(0);

        }
    }

    public void openTranslate(View view) {
        adapter.notifyDataSetChanged();
        //get the position of the selected phrase
            int position = listView.getCheckedItemPosition();
            if (position > -1) {
                selectedPhrase = (String) listView.getItemAtPosition(position);
                getLanguageCode();
                try {
                    //pass the selected phrase to the translation service
                    translationService = initLanguageTranslatorService();
                    new TranslationTask().execute(selectedPhrase);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                Toast.makeText(Translate.this,"Please Select a Phrase",Toast.LENGTH_SHORT).show();
            }
        adapter.notifyDataSetChanged();

        }

    //apply the textToSpeech service
    private TextToSpeech initTextToSpeechService() {
        Authenticator authenticator = new IamAuthenticator("JjV0cmaV1OYRpXo2P30Uwt9Mb_FU_qZGfvBiD71FgjXg");
        TextToSpeech service = new TextToSpeech(authenticator);
        service.setServiceUrl("https://api.eu-gb.text-to-speech.watson.cloud.ibm.com/instances/080526dc-6778-4630-87d5-258ce19cd1d4");
        return service;
    }
    private class SynthesisTask extends AsyncTask<String, Void, String> {
        @Override
        //this execute the work that is to be performed on the separate thread.
        protected String doInBackground(String... params) {
            SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
                    .text(params[0])
                    .voice(SynthesizeOptions.Voice.EN_US_LISAVOICE)
                    .accept(HttpMediaType.AUDIO_WAV)
                    .build();
            try {
                player.playStream(textService.synthesize(synthesizeOptions).execute().getResult());
            } catch (RuntimeException e) {
                e.printStackTrace();
                //we can't make a toast in in background running thread so create uiThread
                runOnUiThread(new Runnable() {
                    public void run() {
                        final Toast toast = Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
            return "Did synthesize";
        }
    }
    //execute the methods when pronounce button pressed
    public void openPronounce(View view) {
        String text = textView.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(getBaseContext(), "Please select a phrase", Toast.LENGTH_SHORT).show();

        } else {
            //execute the method
            new SynthesisTask().execute
                    (textView.getText().toString());
        }
    }

    public void openTranslateAll(View view){
        translateAllBtnChecked =true;
        //get the phrases
        Cursor cursor = databaseHelper.displayAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(Translate.this,"There are no Phrases here",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
        while (cursor.moveToNext()){
            //get the language code from the selected language
            getLanguageCode();
            try {
                //translate the phrase
                translationService = initLanguageTranslatorService();
                new TranslationTask().execute(cursor.getString(0));
            }catch (Exception e){
                Log.e(TAG,e.toString());
            }
        }
        }

        //open the TranslateAll activity
        Intent intent = new Intent(this,TranslateAll.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the state of the TextView
        outState.putString(TEXT_STATE, textView.getText().toString());
    }
}
