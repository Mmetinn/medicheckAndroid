package com.example.a90531.medicheck;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class selectDoctorActivity extends AppCompatActivity implements AsyncResponse {
    String type="getdoctor";
    private ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<String> idler= new ArrayList<>();
    ArrayList<String> doktorlar= new ArrayList<>();
    ArrayList<String> idler2= new ArrayList<>();
    ArrayList<String> doktorlar2= new ArrayList<>();
    EditText etSearch;
    String userId="";
    String doctor_id="";
    final String onay_durumu="onaylanmadi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);
        listView=(ListView)findViewById(R.id.listDoctors);
        etSearch=(EditText)findViewById(R.id.etSearch);


        BackgroundWorker_class backgroundWorker_class = new BackgroundWorker_class(selectDoctorActivity.this);
        backgroundWorker_class.delegate=selectDoctorActivity.this;
        backgroundWorker_class.execute(type);

        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(selectDoctorActivity.this);
        backgroundWorker_class2.delegate=selectDoctorActivity.this;
        backgroundWorker_class2.execute("getkayitlidoktorlar");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(selectDoctorActivity.this);
                backgroundWorker_class2.delegate=selectDoctorActivity.this;
                backgroundWorker_class2.execute("hastaDoktorKayit",idler.get(position),userId);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit(); //SharedPreferences'a kayıt eklemek için editor oluşturuyoruz
                editor.putString("doctorId",idler.get(position));
                Toast.makeText(selectDoctorActivity.this,idler.get(position)+" "+userId,Toast.LENGTH_LONG).show();
                editor.commit();
              /*  BackgroundWorker_class backgroundWorker_class3 = new BackgroundWorker_class(selectDoctorActivity.this);
                backgroundWorker_class3.delegate=selectDoctorActivity.this;
                backgroundWorker_class3.execute("register_doctor_for_patient",idler.get(position),userId);*/

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(selectDoctorActivity.this);
                dlgAlert.setMessage("Doktor kayıt isteğinizi doktornuza göndermek istediğinizden emin misiniz?.");
                dlgAlert.setTitle("MediCheck'ten mesaj var.");
                dlgAlert.setPositiveButton("Hayır",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                dlgAlert.setNegativeButton("Evet",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(selectDoctorActivity.this,MainPageActivity.class);
                                startActivity(i);
                                Toast.makeText(selectDoctorActivity.this,"İsteğiniz gönderildi..",Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                dlgAlert.create().show();

            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                    initListView();
                else
                    searchItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void searchItem(String textToSearch){
        for (String item:doktorlar){
            if(!item.contains(textToSearch)){
                doktorlar.remove(item);
            }

        }
        adapter.notifyDataSetChanged();
    }

    public void initListView(){
        adapter=new ArrayAdapter<String>(this,R.layout.list_item2,R.id.txtitem,doktorlar);
        listView.setAdapter(adapter);
    }


    //verilerimi php dosyasından çekerken en sona hangi php dosyasından geldiğini anlamak için ayırt edici bir string ekledim getdoctor get doctorhasta gibi.
    @Override
    public void processFinish(String output) {

        //doktor_hasta tablosunda benim giriş yapan kullanıcıma ait olan kayıt var mı diye kontorl etmek için
        //sharedpreferenceste kayıtlı olan userId'mi çektim
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");

        String dizi[]=output.trim().split("_");
        int sayac=0;
        int sayac2=0;
        if(dizi[dizi.length-1].equals("getdoctors") && dizi.length>1) {
            String[] a = new String[100];
            //dizi.length-1 olmasının sebebi php dosyasından verileri çekerken en sona doktor verisi mi hasta_doktor
            //verisi mi olduğu bilgisini atıyorum ve bu deger -- ile splitlenemiyor ve dizinin ikinci elmanı mevcut olmuyor
            //bu yüzden doktorlar.add(a[1]) elmaanını en son satır için çekerken indexoutofboundsexeception hatası alıyorum.
            while (sayac < dizi.length-1) {
                a = dizi[sayac].trim().split("--");
                idler.add(a[0]);
                doktorlar.add(a[1]);
                sayac++;
            }
            initListView();
        }else if(dizi[dizi.length-1].equals("getdoktorhasta")&&dizi.length>1){
            String[] a = new String[100];
            while (sayac2 < dizi.length-1) {
                a = dizi[sayac].trim().split("--");
                if(userId.equals(a[1])) {
                    idler2.add(a[1]);
                    doktorlar2.add(a[2]);
                }
                sayac2++;
            }
        }
        //idler ve doktorlar arraylistleri getDoctors ile doluyor
        if(idler2.size()>0 && dizi[dizi.length-1].equals("getdoktorhasta")) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Daha önce doktor seçtiniz.Eğer doktor ekleme istiyorsanız Doktor ekle kısmına tıklayın.");
            dlgAlert.setTitle("MediCheck'ten mesaj var.");
            dlgAlert.setPositiveButton("Doktor Ekle",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            dlgAlert.setNegativeButton("Devam Et",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(selectDoctorActivity.this,MainPageActivity.class);
                            startActivity(i);
                        }
                    }
            );
            dlgAlert.create().show();

            Toast.makeText(getApplicationContext(), "doktor kaydı var", Toast.LENGTH_SHORT).show();
        }
    }
}
