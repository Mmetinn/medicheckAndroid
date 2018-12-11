package com.example.a90531.medicheck;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class acilNumberActivity extends AppCompatActivity {

    AdView adViewAcilNumara_,adViewAcilNumara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acil_number);

        MobileAds.initialize(this,"ca-app-pub-7334446571364171~4227399096");
        adViewAcilNumara_=(AdView)findViewById(R.id.adViewAcilNumara_);
        AdRequest adRequest=new AdRequest.Builder().build();
        adViewAcilNumara_.loadAd(adRequest);
        adViewAcilNumara=(AdView)findViewById(R.id.adViewAcilNumara);
        AdRequest adRequest2=new AdRequest.Builder().build();
        adViewAcilNumara.loadAd(adRequest2);

    }

    @SuppressLint("MissingPermission")
    public void itfaiyeClick(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:110"));
        startActivity(callIntent);
    }
    @SuppressLint("MissingPermission")
    public void ambulansClick(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:112"));
        startActivity(callIntent);
    }
    @SuppressLint("MissingPermission")
    public void polisClick(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:155"));
        startActivity(callIntent);
    }
    @SuppressLint("MissingPermission")
    public void doktorClick(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:113"));
        startActivity(callIntent);
    }
    @SuppressLint("MissingPermission")
    public void saglikDanismaClick(View view){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:184"));
        startActivity(callIntent);
    }
}
