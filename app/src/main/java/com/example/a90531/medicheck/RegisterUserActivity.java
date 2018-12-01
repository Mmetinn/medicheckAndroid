package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterUserActivity extends AppCompatActivity implements AsyncResponse{
    EditText etKulad,etSifre,etKuladTekrar,etSifreTekarar,etAdi,etSoyadi,etEmail,etTelefon,etTc,etHastalik;
    DatePicker dp=null;
    Button btn;
    Button btnTarihSec;
    EditText etTarih;
    Context context = this;
    TextView txDate;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        etKulad=(EditText)findViewById(R.id.kullaniciAdiEt);
        etKuladTekrar=(EditText)findViewById(R.id.kullaniciAdiEt2);
        etSifre=(EditText)findViewById(R.id.sifreEt);
        etSifreTekarar=(EditText)findViewById(R.id.sifreEt2);
        etAdi=(EditText)findViewById(R.id.hastaAdiEt);
        etSoyadi=(EditText)findViewById(R.id.hastaSoyAdiEt);
        etEmail=(EditText)findViewById(R.id.emailEt);
        etTelefon=(EditText)findViewById(R.id.telEt);
        etTc=(EditText)findViewById(R.id.tcEt);
        btnTarihSec = (Button) findViewById(R.id.button_tarih_sec);
        txDate=(TextView)findViewById(R.id.dateTx);
        etHastalik=(EditText)findViewById(R.id.hastaAdiEt);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);

        /*LinearLayout ll = (LinearLayout) findViewById(R.id.linear);
        dp = new DatePicker(RegisterUserActivity.this);
        btn = new Button(RegisterUserActivity.this);
        btn.setBackgroundResource(R.color.colorButton);
        btn.setTextColor(Color.WHITE);
        dp.setMinimumHeight(50);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        ll.addView(dp, lp);
        ll.addView(btn, lp);
        btn.setText("Doğum Tarihiniz:");*/

        btnTarihSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Şimdiki zaman bilgilerini alıyoruz. güncel yıl, güncel ay, güncel gün.
                final Calendar takvim = Calendar.getInstance();
                int yil = takvim.get(Calendar.YEAR);
                int ay = takvim.get(Calendar.MONTH);
                int gun = takvim.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // ay değeri 0 dan başladığı için (Ocak=0, Şubat=1,..,Aralık=11)
                                // değeri 1 artırarak gösteriyoruz.
                                month += 1;
                                // year, month ve dayOfMonth değerleri seçilen tarihin değerleridir.
                                // Edittextte bu değerleri gösteriyoruz.
                                txDate.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        }, yil, ay, gun);
                // datepicker açıldığında set edilecek değerleri buraya yazıyoruz.
                // şimdiki zamanı göstermesi için yukarda tanmladığımz değşkenleri kullanyoruz.

                // dialog penceresinin button bilgilerini ayarlıyoruz ve ekranda gösteriyoruz.
                dpd.setButton(DatePickerDialog.BUTTON_POSITIVE, "Seç", dpd);
                dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", dpd);
                dpd.show();

            }
        });

    }

    public void userKaydiClicked(View view){
        String kulad=etKulad.getText().toString();
        String kuladTekrar=etKuladTekrar.getText().toString();
        String sifre=etSifre.getText().toString();
        String sifreTekrar=etSifreTekarar.getText().toString();
        String adi=etAdi.getText().toString();
        String soyadi=etSoyadi.getText().toString();
        String email=etEmail.getText().toString();
        String telefon=etTelefon.getText().toString();
        String tc=etTc.getText().toString();
        String hastalik=etHastalik.getText().toString();
        String tarih=txDate.getText().toString();
        int selectedId=radioSexGroup.getCheckedRadioButtonId();
        radioSexButton=(RadioButton)findViewById(selectedId);
        String cinsiyet=radioSexButton.getText().toString();

        String type="registered";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=RegisterUserActivity.this;
        backgroundWorker_class2.execute(type,kulad,sifre,adi,soyadi,email,telefon,tc,hastalik,tarih,cinsiyet);
        Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processFinish(String output) {

    }
}
