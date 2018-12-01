package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class olcumGirActivity extends AppCompatActivity  implements AsyncResponse{
    LinearLayout linnear;
    EditText etDeger,etAciklama;
    String deger,aciklama;
    String hangiOlcum=null;
    String userId;
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMM");
    SimpleDateFormat dateFormatTx= new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String date = dateFormatTx.format(new Date().getTime());
    String []array= new String[400];
    ArrayList<String> olcumlerList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olcum_gir);
        linnear=(LinearLayout)findViewById(R.id.linList);
        etDeger = (EditText)findViewById(R.id.degerEt);
        etAciklama = (EditText)findViewById(R.id.aciklamaEt);



        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");
        Toast.makeText(getApplicationContext()," hastanın idsi  "+userId,Toast.LENGTH_SHORT).show();
        BackgroundWorker_class backgroundWorker_class = new BackgroundWorker_class(olcumGirActivity.this);
        backgroundWorker_class.delegate=olcumGirActivity.this;
        backgroundWorker_class.execute("getolcumler",userId);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            hangiOlcum= "";
        } else {
            hangiOlcum= extras.getString("hangiOlcum");
        }


      //  Toast.makeText(getApplicationContext(),"olcum degeri--> "+hangiOlcum,Toast.LENGTH_SHORT).show();
    }

    public void btnClicked(View view){
        aciklama=etAciklama.getText().toString();
        deger = etDeger.getText().toString();
        createCard();
    }

    private void createCardAll() {
        int sayac=0;
        while (sayac<olcumlerList.size()){
            String dizi[]=olcumlerList.get(sayac).split("--");
            if(dizi[3].equals(hangiOlcum)) {
                CardView cv = new CardView(olcumGirActivity.this);
                ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                linnear.addView(cv, lp);
                TextView tx1 = new TextView(this);
                TextView tx2 = new TextView(this);
                TextView tx3 = new TextView(this);
                tx1.setText("Ölçüm   :"+dizi[1]);
                tx2.setText("Açıklama:"+dizi[2]);
                tx3.setText("Tarih   :"+dizi[5]);
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.VERTICAL);
                cv.addView(ll, lp);
                ll.addView(tx1, lp);
                ll.addView(tx2, lp);
                ll.addView(tx3, lp);
            }
            sayac++;
        }
    }

    private void createCard() {
        String type="olcumgir";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=olcumGirActivity.this;
        backgroundWorker_class2.execute(type,deger,aciklama,hangiOlcum,userId,date);
        Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
        CardView cv = new CardView(olcumGirActivity.this);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        linnear.addView(cv, lp);
        TextView tx1 = new TextView(this);
        TextView tx2 = new TextView(this);
        TextView tx3 = new TextView(this);
        tx1.setText(deger);
        tx2.setText(aciklama);
        tx3.setText(date);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        cv.addView(ll, lp);
        ll.addView(tx1, lp);
        ll.addView(tx2, lp);
        ll.addView(tx3, lp);;
    }

    @Override
    public void processFinish(String output) {
        array=output.trim().split("_");
        int a=0;
        while (a<array.length){
            olcumlerList.add(array[a]);
            a++;
        }
        if(!output.trim().equals("insert successful")&&!output.trim().equals(""))
            createCardAll();
    }
}
