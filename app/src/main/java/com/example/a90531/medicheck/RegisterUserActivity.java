package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    Button profilepicBtn;
    ImageView profilePicIv;
    PopupMenu popup;
    private static int RESULT_LOAD_IMAGE_GALERI=101;
    private static int RESULT_LOAD_IMAGE_KAMERA=102;
    Bitmap thumbnail;
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
        profilepicBtn=(Button)findViewById(R.id.btnProfilepic);
        profilePicIv=(ImageView)findViewById(R.id.ivProfilePic);

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

        popup = new PopupMenu(RegisterUserActivity.this, profilepicBtn);
        popup.getMenuInflater().inflate(R.menu.kamera_galeri, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.kameraAc:
                        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentKamera,RESULT_LOAD_IMAGE_KAMERA);
                        return  true;
                    case R.id.galeriAc:
                        Intent intentGaleri = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intentGaleri,RESULT_LOAD_IMAGE_GALERI);
                        return true;
                    default :
                        return false;
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE_GALERI){
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                    profilePicIv.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }else if(requestCode == RESULT_LOAD_IMAGE_KAMERA){
            if(resultCode == RESULT_OK) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        profilePicIv.setMaxWidth(200);
        profilePicIv.setImageBitmap(thumbnail);
    }

    public void resimClicked(View view){
        popup.show();
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
        profilePicIv.setDrawingCacheEnabled(true);
        profilePicIv.buildDrawingCache();
        Bitmap bitmap = profilePicIv.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        String dataStr = Base64.encodeToString(data, Base64.DEFAULT);

        String type="registered";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=RegisterUserActivity.this;
        backgroundWorker_class2.execute(type,kulad,sifre,adi,soyadi,email,telefon,tc,hastalik,tarih,cinsiyet,dataStr);
        Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processFinish(String output) {

    }
}
