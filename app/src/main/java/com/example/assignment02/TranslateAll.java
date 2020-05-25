package com.example.assignment02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TranslateAll extends AppCompatActivity {
    Button arabicBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translateall_activity);
        arabicBtn = findViewById(R.id.arabic_button);
    }

    public void openArabic(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ar"; //set the code
        intent.putExtra("codeStatus",code); // pass the code to other intent
        startActivity(intent);
    }
    public void openBulgarian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "bg";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openCzech(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "cs";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openDanish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "da";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }

    public void openGerman(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "de";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openGreek(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "el";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openSpanish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "es";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openEstonian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "et";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openFinnish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "fi";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openFrench(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "fr";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openIrish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ga";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openHebrew(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "he";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openHindi(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "hi";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }

    public void openCroatian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "hr";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openHungarian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "hu";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openItalian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "it";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openJapanese(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ja";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openKorean(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ko";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openLithuanian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "lt";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openLatvian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "lv";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openMalay(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ms";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openNorwegian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "nb";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openDutch(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "nl";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openPolish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "pl";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openPortuguese(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "pt";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openRomanian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ro";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openRussian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ru";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openSlovakian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "sk";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openSlovenian(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "sl";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openSwedish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "sv";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openThai(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "th";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openTurkish(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "tr";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openUrdu(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "ur";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openVietnamese(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "vi";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openSimplifiedChinese(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "zh";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }
    public void openTraditionalChinese(View view){
        Intent intent = new Intent(this,PopUp.class);
        String code = "zh-TW";
        intent.putExtra("codeStatus",code);
        startActivity(intent);
    }

}
