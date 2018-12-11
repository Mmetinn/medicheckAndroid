package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class ilacListeActivity extends AppCompatActivity implements AsyncResponse {
    TextView tx1,tx2,tx3,tx4,tx5;
    GridLayout gl;
    Button deleteBtn,updateBtn;
    ArrayList<String> arrayID=new ArrayList<>();
    ArrayList<String> list=new ArrayList<>();
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilac_liste);

        gl = (GridLayout)findViewById(R.id.gridimIlac);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");
        String type="listMedic";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=ilacListeActivity.this;
        backgroundWorker_class2.execute(type,userId);
        Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();

        listele();
    }

   /* public void listele(){
        ilacSaatiDB db = new ilacSaatiDB(ilacListeActivity.this);
        ArrayList<String> list = db.DBArray();
        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(ilacListeActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,list){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1)
                {
                    view.setBackgroundColor(Color.parseColor("#88A0D9"));
                }
                else
                {
                    view.setBackgroundColor(Color.parseColor("#9FB4D9"));
                }
                return view;
            }
        };
        ilacList.setAdapter(adaptor);
    }*/
   public void listele(){
       /*ilacSaatiDB db = new ilacSaatiDB(ilacListeActivity.this);
       ArrayList<String> list = db.DBArray();*/

       TextView info=(TextView)findViewById(R.id.infoTx);
       if(list.size()!=0){
           info.setVisibility(TextView.GONE);
       }

       ArrayList<String> ilacAdList=new ArrayList<>();
       ArrayList<String> aciklamaList=new ArrayList<>();
       ArrayList<String> acTokList=new ArrayList<>();
       ArrayList<String> kacDefaList=new ArrayList<>();
       ArrayList<String> sureList=new ArrayList<>();
       int sayac=0;
       String dizi[]=new String[100];
       while (list.size() > sayac) {
           dizi = list.get(sayac).split("--");
           ilacAdList.add(dizi[0]);
           aciklamaList.add(dizi[1]);
           acTokList.add(dizi[2]);
           kacDefaList.add(dizi[3]);
           sureList.add(dizi[4]);
          // arrayID.add(dizi[5]);
           sayac++;
       }
       LinearLayout ll = new LinearLayout(ilacListeActivity.this);
       ll.setOrientation(LinearLayout.VERTICAL);
       ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
       ActionBar.LayoutParams lpSize = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
       ActionBar.LayoutParams lpSizeWrap = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);



       gl.addView(ll, lp);
       int sayac2=0;
       while (list.size()>sayac2) {
           deleteBtn = new Button(ilacListeActivity.this);
           updateBtn = new Button(ilacListeActivity.this);
           tx1 = new TextView(ilacListeActivity.this);
           tx2 = new TextView(ilacListeActivity.this);
           tx3 = new TextView(ilacListeActivity.this);
           tx4 = new TextView(ilacListeActivity.this);
           tx5 = new TextView(ilacListeActivity.this);

/*           tx1.setBackgroundResource(R.drawable.border);
           tx2.setBackgroundResource(R.drawable.border);
           tx3.setBackgroundResource(R.drawable.border);
           tx4.setBackgroundResource(R.drawable.border);
           tx5.setBackgroundResource(R.drawable.border);*/

          // deleteBtn.setBackgroundResource(R.drawable.ic_delete_icon);
        //   updateBtn.setBackgroundResource(R.drawable.ic_reload);
           tx1.setText("İlacın Adı"+": "+ilacAdList.get(sayac2));
           tx2.setText("İlacın Açıklaması"+": "+aciklamaList.get(sayac2));
           tx3.setText("Aç Tok"+": "+acTokList.get(sayac2));
           tx4.setText("İlaçan Mevcut Mu"+": "+kacDefaList.get(sayac2));
           tx5.setText("Ne kadar Kullanılacak"+": "+sureList.get(sayac2));
           CardView cv = new CardView(ilacListeActivity.this);
           LinearLayout ll2 = new LinearLayout(ilacListeActivity.this);
           LinearLayout ll3 = new LinearLayout(ilacListeActivity.this);
           LinearLayout ll4 = new LinearLayout(ilacListeActivity.this);

           ll.setOrientation(LinearLayout.VERTICAL);
           ll.setGravity(Gravity.CENTER);
           ll.setBackgroundColor(Color.WHITE);
           ll.addView(cv,lp);
           cv.addView(ll4,lpSize);
           ll4.addView(ll2,lp);
           ll4.addView(ll3,lp);
            ll4.setGravity(Gravity.CENTER);
           ll2.setOrientation(LinearLayout.VERTICAL);
           ll2.addView(tx1, lp);
           ll2.addView(tx2, lp);
           ll2.addView(tx3, lp);
           ll2.addView(tx4, lp);
           ll2.addView(tx5, lp);
           ll3.setGravity(Gravity.CENTER);
           ll3.addView(deleteBtn,lpSizeWrap);
           ll3.addView(updateBtn,lpSizeWrap);
            final int index=sayac2;
           /*deleteBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           switch (which){
                               case DialogInterface.BUTTON_POSITIVE:
                                   DB.ilacDelete(Integer.parseInt(arrayID.get(index)));
                                   Toast.makeText(getApplicationContext(),R.string.delete_registration_toast,Toast.LENGTH_SHORT).show();
                                   ArrayList<String> silinecek_alarmlar=new ArrayList<>();
                                   silinecek_alarmlar=DB.DBArrayHangiIlacinAlarmIdleri(Integer.parseInt(arrayID.get(index)));
                                   int s=0;
                                while (s<silinecek_alarmlar.size()){
                                       AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                       Intent myIntent = new Intent(getApplicationContext(), myBroadcast.class);
                                       int id = Integer.parseInt(silinecek_alarmlar.get(s));
                                       PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id+1, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                       alarmManager.cancel(pendingIntent);

                                       AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                       Intent myIntent2 = new Intent(getApplicationContext(), uygulamaBaslat.class);

                                       PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), id+1, myIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
                                       alarmManager2.cancel(pendingIntent2);
                                       s++;
                                   }
                                   DB.alarmDeleteById(Integer.parseInt(arrayID.get(index)));

                                   gl.removeAllViews();
                                   listele();
                                   break;
                               case DialogInterface.BUTTON_NEGATIVE:
                                   break;

                           }
                       }
                   };
                   AlertDialog.Builder builder = new AlertDialog.Builder(ilacListeActivity.this);
                   builder.setMessage(R.string.delete_ques_alert_medic).setPositiveButton(R.string.yes_text,dialogClickListener).setNegativeButton(R.string.no_text,dialogClickListener).show();

               }
           });*/

         /*  updateBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener(){
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           switch (which){
                               case DialogInterface.BUTTON_POSITIVE:
                                   Intent intent = new Intent(ilacListeActivity.this,ilacKaydetActivity.class);
                                   intent.putExtra("ilac_id",arrayID.get(index));
                                   startActivity(intent);
                                   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                   break;
                               case DialogInterface.BUTTON_NEGATIVE:
                                   break;

                           }
                       }
                   };
                   AlertDialog.Builder builder = new AlertDialog.Builder(ilacListeActivity.this);
                   builder.setMessage(R.string.update_alert).setPositiveButton(R.string.yes_text,dialogClickListener).setNegativeButton(R.string.no_text,dialogClickListener).show();

               }
           });*/

           sayac2++;
       }
   }

    @Override
    public void processFinish(String output) {
        String dizi[] = output.trim().split("_");
        int sayac = 0;
        if (dizi[dizi.length - 1].equals("listMedic") && dizi.length > 1) {
            String[] a = new String[100];
            //dizi.length-1 olmasının sebebi php dosyasından verileri çekerken en sona doktor verisi mi hasta_doktor
            //verisi mi olduğu bilgisini atıyorum ve bu deger -- ile splitlenemiyor ve dizinin ikinci elmanı mevcut olmuyor
            //bu yüzden doktorlar.add(a[1]) elmaanını en son satır için çekerken indexoutofboundsexeception hatası alıyorum.
            while (sayac < dizi.length - 1) {
                a = dizi[sayac].trim().split("--");
                list.add(a[0]+"--"+a[1]+"--"+a[2]+"--"+a[3]+"--"+a[4]);
                sayac++;
            }
        }
        listele();
    }
}