package com.example.a90531.medicheck;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements AsyncResponse {
    private BottomNavigationView bottomNavigationView;
    private NavigationView nv;
    private DrawerLayout drawerLayout;
    private DecoView agirlikDc,kanSekeriDc,adimDc,nabizDc,kanBasinciDc,alinanKaloriDc,harcananKaloriDc,kolestrolDc,vucutSicakligiDc;
    private String userId;
    private ArrayList<String> olcumlerList = new ArrayList<>();
    private String []array= new String[400];
    private TextView txAgirlik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        nv=(NavigationView)findViewById(R.id.naviMain);
        nv.setItemIconTintList(null);
        nv.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        agirlikDc=(DecoView)findViewById(R.id.agirlikDecode);
        kanSekeriDc=(DecoView)findViewById(R.id.kanSekeriDecode);
        adimDc=(DecoView)findViewById(R.id.adimDecode);
        nabizDc=(DecoView)findViewById(R.id.nabizDecode);
        kanBasinciDc=(DecoView)findViewById(R.id.kanBasinciDecode);
        alinanKaloriDc=(DecoView)findViewById(R.id.alinanKaloriDecode);
        harcananKaloriDc=(DecoView)findViewById(R.id.harcananKaloriDecode);
        kolestrolDc=(DecoView)findViewById(R.id.kolestrolDecode);
        vucutSicakligiDc=(DecoView)findViewById(R.id.vucutSicakligiDecode);
        txAgirlik=(TextView)findViewById(R.id.agirlikTx);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");
        Toast.makeText(getApplicationContext()," hastanın idsi  "+userId,Toast.LENGTH_SHORT).show();
        BackgroundWorker_class backgroundWorker_class = new BackgroundWorker_class(MainPageActivity.this);
        backgroundWorker_class.delegate=MainPageActivity.this;
        backgroundWorker_class.execute("getolcumler",userId);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.olcumGir:
                        Intent intent = new Intent(MainPageActivity.this,olcumKaydetActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.randevu:
                        Intent intent2 = new Intent(MainPageActivity.this,HastaneRandevuActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.randevu_list:
                        Intent intent3 = new Intent(MainPageActivity.this,randevuListActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.ilac_kaydet:
                        Intent intent4 = new Intent(MainPageActivity.this,ilacKaydetActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.ilac_listele:
                        Intent intent5 = new Intent(MainPageActivity.this,ilacListeActivity.class);
                        startActivity(intent5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.acil_numara:
                        Intent intent6 = new Intent(MainPageActivity.this,acilNumberActivity.class);
                        startActivity(intent6);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.select_doctor:
                        Intent intent7 = new Intent(MainPageActivity.this,selectDoctorActivity.class);
                        startActivity(intent7);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                //drawerLayout.closeDrawer();
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.message_menu, menu);
        getMenuInflater().inflate(R.menu.right_top_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.message_activity:
                startActivity(new Intent(MainPageActivity.this,MessagesMainActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }
    void decoViewCreate(){
        int sayac=0;
        ArrayList<String> agirlikArray = new ArrayList<>();
        ArrayList<String> kanSekerArray = new ArrayList<>();
        ArrayList<String> kanBasinciArray = new ArrayList<>();
        ArrayList<String> adimSayArray = new ArrayList<>();
        ArrayList<String> nabizArray = new ArrayList<>();
        ArrayList<String> tuketilenKaloriArray = new ArrayList<>();
        ArrayList<String> harcananKaloriArray = new ArrayList<>();
        ArrayList<String> kolestrolArray = new ArrayList<>();
        ArrayList<String> vucutSicakligiArray = new ArrayList<>();
        if(olcumlerList.size()!=1) {
            while (sayac < olcumlerList.size()) {
                String dizi[] = olcumlerList.get(sayac).split("--");
                switch (dizi[3]) {
                    case "1":
                        //agirlik
                        agirlikArray.add(dizi[1]);
                        break;
                    case "2":
                        //kansekeri
                        kanSekerArray.add(dizi[1]);
                        break;
                    case "3":
                        //kanbasinci
                        kanBasinciArray.add(dizi[1]);
                        break;
                    case "4":
                        //adim say
                        adimSayArray.add(dizi[1]);
                        break;
                    case "5":
                        //nabiz
                        nabizArray.add(dizi[1]);
                        break;
                    case "6":
                        //tuketilen kalori
                        tuketilenKaloriArray.add(dizi[1]);
                        break;
                    case "7":
                        //harcanan kalori
                        harcananKaloriArray.add(dizi[1]);
                        break;
                    case "8":
                        //kolestrol
                        kolestrolArray.add(dizi[1]);
                        break;
                    case "9":
                        //vucut sicakligi
                        vucutSicakligiArray.add(dizi[1]);
                        break;
                }
                sayac++;
            }
        }
         //agirlik
        if(!agirlikArray.isEmpty()){
            final SeriesItem seriesItem_1_1 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_1_1 = agirlikDc.addSeries(seriesItem_1_1);
            final SeriesItem seriesItem2_1_1 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_1_1 = agirlikDc.addSeries(seriesItem2_1_1);

            agirlikDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_1_1)
                    .build());
            agirlikDc.addEvent(new DecoEvent.Builder(Integer.parseInt(agirlikArray.get(agirlikArray.size()-1)))
                    .setIndex(series1Index_1_1)
                    .setDelay(5000)
                    .build());
        }
        //kansekeri
        if(!kanSekerArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = kanSekeriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = kanSekeriDc.addSeries(seriesItem2_2);
            kanSekeriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            kanSekeriDc.addEvent(new DecoEvent.Builder(Integer.parseInt(kanSekerArray.get(kanSekerArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //kanbasinci
        if(!kanBasinciArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = kanBasinciDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = kanBasinciDc.addSeries(seriesItem2_2);
            kanBasinciDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            kanBasinciDc.addEvent(new DecoEvent.Builder(Integer.parseInt(kanBasinciArray.get(kanBasinciArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //adim say
        if(!adimSayArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = adimDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = adimDc.addSeries(seriesItem2_2);
            adimDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            adimDc.addEvent(new DecoEvent.Builder(Integer.parseInt(adimSayArray.get(adimSayArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //nabiz
        if(!nabizArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = nabizDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = nabizDc.addSeries(seriesItem2_2);
            nabizDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            nabizDc.addEvent(new DecoEvent.Builder(Integer.parseInt(nabizArray.get(nabizArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //tuketilen kalori
        if(!tuketilenKaloriArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = alinanKaloriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = alinanKaloriDc.addSeries(seriesItem2_2);
            alinanKaloriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            alinanKaloriDc.addEvent(new DecoEvent.Builder(Integer.parseInt(tuketilenKaloriArray.get(tuketilenKaloriArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //harcanan kalori
        if(!harcananKaloriArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = harcananKaloriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = harcananKaloriDc.addSeries(seriesItem2_2);
            harcananKaloriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            harcananKaloriDc.addEvent(new DecoEvent.Builder(Integer.parseInt(harcananKaloriArray.get(harcananKaloriArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //kolestrol
        if(!kolestrolArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = kolestrolDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = kolestrolDc.addSeries(seriesItem2_2);
            kolestrolDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            kolestrolDc.addEvent(new DecoEvent.Builder(Integer.parseInt(kolestrolArray.get(kolestrolArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }

        //vucut sicakligi
        if(!vucutSicakligiArray.isEmpty()){
            final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();
            int backIndex_2 = vucutSicakligiDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = vucutSicakligiDc.addSeries(seriesItem2_2);
            vucutSicakligiDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            vucutSicakligiDc.addEvent(new DecoEvent.Builder(Integer.parseInt(vucutSicakligiArray.get(vucutSicakligiArray.size()-1)))
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }




        if(agirlikArray.isEmpty()) {
            final SeriesItem seriesItem_1 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_1 = agirlikDc.addSeries(seriesItem_1);
            final SeriesItem seriesItem2_1 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_1 = agirlikDc.addSeries(seriesItem2_1);

            agirlikDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_1)
                    .setDisplayText("aaaa")
                    .build());
            agirlikDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_1)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        final SeriesItem seriesItem_2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, 50, 0)
                .build();

        if(kanSekerArray.isEmpty()) {


            int backIndex_2 = kanSekeriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_2 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_2 = kanSekeriDc.addSeries(seriesItem2_2);
            kanSekeriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_2)
                    .build());
            kanSekeriDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_2)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(adimSayArray.isEmpty()) {
            final SeriesItem seriesItem_3 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_3 = adimDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_3 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_3 = adimDc.addSeries(seriesItem2_3);
            adimDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_3)
                    .build());
            adimDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_3)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(nabizArray.isEmpty()) {
            final SeriesItem seriesItem_4 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_4 = nabizDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_4 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_4 = nabizDc.addSeries(seriesItem2_4);
            nabizDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_4)
                    .build());
            nabizDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_4)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(kanBasinciArray.isEmpty()) {
            final SeriesItem seriesItem_5 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_5 = kanBasinciDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_5 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_5 = kanBasinciDc.addSeries(seriesItem2_5);
            kanBasinciDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_5)
                    .build());
            kanBasinciDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_5)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(tuketilenKaloriArray.isEmpty()) {
            final SeriesItem seriesItem_6 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_6 = alinanKaloriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_6 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_6 = alinanKaloriDc.addSeries(seriesItem2_6);
            alinanKaloriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_6)
                    .build());
            alinanKaloriDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_6)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(harcananKaloriArray.isEmpty()) {
            final SeriesItem seriesItem_7 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_7 = harcananKaloriDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_7 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_7 = harcananKaloriDc.addSeries(seriesItem2_7);
            harcananKaloriDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_7)
                    .build());
            harcananKaloriDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_7)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(kolestrolArray.isEmpty()) {
            final SeriesItem seriesItem_8 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_8 = kolestrolDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_8 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_8 = kolestrolDc.addSeries(seriesItem2_8);
            kolestrolDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_8)
                    .build());
            kolestrolDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_8)
                    .setDelay(5000)
                    .build());
        }
        //////////////////////////////////////////////////////
        if(vucutSicakligiArray.isEmpty()) {
            final SeriesItem seriesItem_9 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                    .setRange(0, 50, 0)
                    .build();

            int backIndex_9 = vucutSicakligiDc.addSeries(seriesItem_2);
            final SeriesItem seriesItem2_9 = new SeriesItem.Builder(Color.parseColor("#FFFF8800"))
                    .setRange(0, 250, 0)
                    .build();
            int series1Index_9 = vucutSicakligiDc.addSeries(seriesItem2_9);
            vucutSicakligiDc.addEvent(new DecoEvent.Builder(50)
                    .setIndex(backIndex_9)
                    .build());
            vucutSicakligiDc.addEvent(new DecoEvent.Builder(0)
                    .setIndex(series1Index_9)
                    .setDelay(5000)
                    .build());
        }
          //  sayac++;
       // }
    }

    @Override
    public void processFinish(String output) {
        array=output.trim().split("_");
        int a=0;
        while (a<array.length){
            olcumlerList.add(array[a]);
            a++;
        }
        //if(array.length>1)
            decoViewCreate();
    }
}
