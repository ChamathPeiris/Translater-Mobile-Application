package com.example.assignment02;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;


//https://www.google.com/search?q=how+to+display+a+pop+up+window+in+android&rlz=1C1CHBF_enLK880LK880&oq=how+to+display+a+pop+up+wind&aqs=chrome.1.69i57j33l7.14139j0j1&sourceid=chrome&ie=UTF-8#kpvalbx=_CcmVXpCNKKHcz7sPvYuYmAk44
public class PopUp extends Activity {

    TextView display;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        databaseHelper = new DatabaseHelper(this);
        displayAll();
    }

    public void displayAll(){
        Bundle bundle = getIntent().getExtras();
        String code = bundle.getString("codeStatus"); //Get code value from the TranslateAll Activity
        Cursor cursor = databaseHelper.displayAllTranslate(code);
        Cursor cursor1 = databaseHelper.displayAllData();


        if (cursor.getCount() == 0){
            Toast.makeText(PopUp.this,"There are no Phrases here",Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor1.moveToNext()&&cursor.moveToNext()){
            stringBuffer.append(cursor1.getString(0)+" - "+cursor.getString(0) + "\n\n");
        }

        display = findViewById(R.id.popup_textview);
        display.setText(stringBuffer.toString());

    }
}
