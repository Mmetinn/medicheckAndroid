package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class randevuListActivity extends AppCompatActivity implements AsyncResponse {
    int sayac=0;
    String dizi[] = new String[100];
    String hastaneAd[] = new String[100];
    String poliklinikAd[] = new String[100];
    String doktorAd[] = new String[100];
    String tarih[] = new String[100];
    String saat[] = new String[100];
    TextView tx1,tx2,tx3,tx4,tx5;
    GridLayout gl;
    String userId;
    ArrayList<String> idler = new ArrayList<>();
    ArrayList<String> randevu = new ArrayList<>();
    ArrayList<String> idler2 = new ArrayList<>();
    ArrayList<String> doktorlar2 = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_list);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = sharedPreferences.getString("userId", "null");

        String type="listAppointment";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=randevuListActivity.this;
        backgroundWorker_class2.execute(type,userId);

    }

    @Override
    public void processFinish(String output) {

        String dizi[] = output.trim().split("_");
        int sayac = 0;
        int sayac2 = 0;
        if (dizi[dizi.length - 1].equals("listAppointment") && dizi.length > 1) {
            String[] a = new String[100];
            //dizi.length-1 olmasının sebebi php dosyasından verileri çekerken en sona doktor verisi mi hasta_doktor
            //verisi mi olduğu bilgisini atıyorum ve bu deger -- ile splitlenemiyor ve dizinin ikinci elmanı mevcut olmuyor
            //bu yüzden doktorlar.add(a[1]) elmaanını en son satır için çekerken indexoutofboundsexeception hatası alıyorum.
            while (sayac < dizi.length - 1) {
                a = dizi[sayac].trim().split("--");
                randevu.add(a[0]+" "+a[1]+" "+a[2]);
                sayac++;
            }

            ArrayList<String> arrayRandevu = randevu;
            gl = (GridLayout) findViewById(R.id.gridimr);
            gl.setRowCount(arrayRandevu.size());

           // TextView info = (TextView) findViewById(R.id.infoTx);
            /*if (arrayRandevu.size() != 0) {
                info.setVisibility(TextView.GONE);
            }*/

         /*   while (arrayRandevu.size() > sayac) {
                dizi = arrayRandevu.get(sayac).split("--");
                hastaneAd[sayac] = dizi[0];
                poliklinikAd[sayac] = dizi[1];
                doktorAd[sayac] = dizi[2];
                tarih[sayac] = dizi[3];
                saat[sayac] = dizi[4];
                sayac++;
            }*/
            int j = 0;
            LinearLayout ll = new LinearLayout(randevuListActivity.this);
            ll.setOrientation(LinearLayout.VERTICAL);
            ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            gl.addView(ll, lp);
            final Calendar current = Calendar.getInstance();
            while (j < arrayRandevu.size()) {
                TextView t = new TextView(randevuListActivity.this);
               /* if (Long.parseLong(milliss.get(j)) < current.getTimeInMillis()) {
                    t.setText("Randevu Zamanı Geçmiş");
                }*/
                tx1 = new TextView(randevuListActivity.this);
                /*tx2 = new TextView(randevuListActivity.this);
                tx3 = new TextView(randevuListActivity.this);
                tx4 = new TextView(randevuListActivity.this);
                tx5 = new TextView(randevuListActivity.this);*/
                tx1.setText("Randevu Bilgileri" + ": " + arrayRandevu.get(j));
                /*tx2.setText(this.getString(R.string.policlinic) + ": " + poliklinikAd[j]);
                tx3.setText(this.getString(R.string.doctor_name) + ": " + doktorAd[j]);
                tx4.setText(this.getString(R.string.date_text) + ": " + tarih[j]);
                tx5.setText(this.getString(R.string.hour_text) + ": " + saat[j]);
                tx4.setTextColor(Color.parseColor("#0055ff"));
                tx5.setTextColor(Color.parseColor("#0055ff"));
                t.setTextColor(Color.parseColor("#FF0000"));*/
                CardView cv = new CardView(randevuListActivity.this);
                cv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "cv.Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
                LinearLayout ll2 = new LinearLayout(randevuListActivity.this);
                ll.addView(cv, lp);
                cv.addView(ll2, lp);
                ll2.setOrientation(LinearLayout.VERTICAL);
                ll2.addView(tx1, lp);
                /*ll2.addView(tx2, lp);
                ll2.addView(tx3, lp);
                ll2.addView(tx4, lp);
                ll2.addView(tx5, lp);*/
                ll2.addView(t, lp);
                j++;
            }
        }
    }
}