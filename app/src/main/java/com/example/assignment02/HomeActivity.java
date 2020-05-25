package com.example.assignment02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    public void OpenAddPhrases(View view) {
        Intent intent = new Intent(this,AddPhrases.class);
        startActivity(intent);
    }

    public void OpenDisplayPhrases(View view) {
        Intent intent = new Intent(this,DisplayPhrases.class);
        startActivity(intent);
    }

    public void OpenEditPhrases(View view) {
        Intent intent = new Intent(this, EditPhrases.class);
        startActivity(intent);
    }

    public void OpenLanguage(View view) {
        Intent intent = new Intent(this,LanguageSubscription.class);
        startActivity(intent);
    }

    public void OpenTranslate(View view) {
        Intent intent = new Intent(this,Translate.class);
        startActivity(intent);
    }


}
