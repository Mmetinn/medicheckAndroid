package com.example.a90531.medicheck;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RandevuKaydetActivity extends AppCompatActivity implements AsyncResponse {
    String hastaneAd, polikinikAd, doktorAd, saat, tarih;
    EditText hastaneAdEt, poliklinikAdEt, doktorAdEt;
    int dpYear, dpMonth, dpDay, tpHour, tpMinute;
    long mills;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<String> idler = new ArrayList<>();
    List<String> doktorlar = new ArrayList<>();
    ArrayList<String> idler2 = new ArrayList<>();
    ArrayList<String> doktorlar2 = new ArrayList<>();
    EditText etSearch;
    String userId = "";
    Spinner doctorsSp;
    TextView txGunSec,txSaatSec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_kaydet);

        doctorsSp = (Spinner) findViewById(R.id.spDoctors);
        txGunSec=(TextView)findViewById(R.id.gunSecTx);
        txSaatSec=(TextView)findViewById(R.id.saatSecTx);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = sharedPreferences.getString("userId", "null");

        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(RandevuKaydetActivity.this);
        backgroundWorker_class2.delegate = RandevuKaydetActivity.this;
        backgroundWorker_class2.execute("getPatientsDoctors",userId);

    }

    /*public void googleAra(View view){
        Uri uri = Uri.parse("http://www.google.com/#q="+hastaneAdEt.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }*/
    public void tarhiSecClick(View view) {
        final Calendar takvim = Calendar.getInstance();
        int yil = takvim.get(Calendar.YEAR);
        int ay = takvim.get(Calendar.MONTH);
        int gun = takvim.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd = new DatePickerDialog(RandevuKaydetActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dpYear = year;
                        dpMonth = month;
                        dpDay = dayOfMonth;
                        tarih = String.valueOf(year) + "." + String.valueOf(month) + "." + String.valueOf(dayOfMonth);
                    }
                }, yil, ay, gun);
        dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
        dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
        dpd.show();
        txGunSec.setText(tarih);

    }

    public void zamanClicked(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        final Calendar current = Calendar.getInstance();
        mTimePicker = new TimePickerDialog(RandevuKaydetActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tpHour = selectedHour;
                tpMinute = selectedMinute;
                saat = String.valueOf(selectedHour) + ":" + String.valueOf(selectedMinute);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
        txSaatSec.setText(saat);
    }

     public void randevuClicked(View view){
         String secilenDoktorId=idler.get(doctorsSp.getSelectedItemPosition());
         String type="registerAppointment";
         BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
         backgroundWorker_class2.delegate=RandevuKaydetActivity.this;
         backgroundWorker_class2.execute(type,saat,tarih,userId,secilenDoktorId);
         Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
         Toast.makeText(getApplicationContext(),"Radevu isteği başarıyla gönderildi.",Toast.LENGTH_SHORT).show();
         setAlarmNotification();
     }

    public void setAlarmNotification() {
        AlarmManager mgrAlarm = (AlarmManager) this.getSystemService(ALARM_SERVICE);

        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, dpYear);
        cal.set(Calendar.MONTH, dpMonth);
        cal.set(Calendar.DAY_OF_MONTH, dpDay);
        cal.set(Calendar.HOUR_OF_DAY, tpHour);
        cal.set(Calendar.MINUTE, tpMinute);
        cal.set(Calendar.SECOND, 0);
        mills = cal.getTimeInMillis();
        mills -= 1000 * 60 * 60;
        Intent intent = new Intent(RandevuKaydetActivity.this, AppointmentNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(RandevuKaydetActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mgrAlarm.set(AlarmManager.RTC_WAKEUP, mills, pendingIntent);


    }

    @Override
    public void processFinish(String output) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = sharedPreferences.getString("userId", "null");

        String dizi[] = output.trim().split("_");
        int sayac = 0;
        int sayac2 = 0;
        if (dizi[dizi.length - 1].equals("patientsDoctors") && dizi.length > 1) {
            String[] a = new String[100];
            //dizi.length-1 olmasının sebebi php dosyasından verileri çekerken en sona doktor verisi mi hasta_doktor
            //verisi mi olduğu bilgisini atıyorum ve bu deger -- ile splitlenemiyor ve dizinin ikinci elmanı mevcut olmuyor
            //bu yüzden doktorlar.add(a[1]) elmaanını en son satır için çekerken indexoutofboundsexeception hatası alıyorum.
            while (sayac < dizi.length - 1) {
                a = dizi[sayac].trim().split("--");
                idler.add(a[0]);
                doktorlar.add(a[1]);
                sayac++;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    RandevuKaydetActivity.this,
                    android.R.layout.simple_spinner_item,doktorlar
            );

            doctorsSp.setAdapter(adapter);
        } else if (dizi[dizi.length - 1].equals("getkayitlidoktorlar") && dizi.length > 1) {
            String[] a = new String[100];
            while (sayac2 < dizi.length - 1) {
                a = dizi[sayac].trim().split("--");
                if (userId.equals(a[1])) {
                    idler2.add(a[1]);
                    doktorlar2.add(a[2]);
                }
                sayac2++;
            }
        }
    }
}
