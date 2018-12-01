package com.example.a90531.medicheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class olcumKaydetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olcum_kaydet);
    }
    public void agirlikClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","1");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void kanSekeriClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","2");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void kanBasinciClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","3");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void adimSayClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","4");
        startActivity(intent);
    }
    public void nabizClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","5");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void tuketilenKaloriClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","6");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void harcananKaloriClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","7");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void kolestrolClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","8");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void vucutSicakligiClicked(View view){
        Intent intent = new Intent(olcumKaydetActivity.this,olcumGirActivity.class);
        intent.putExtra("hangiOlcum","9");
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
