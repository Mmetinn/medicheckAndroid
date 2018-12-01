package com.example.a90531.medicheck;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "Mobilhanem GCM";
    GoogleCloudMessaging gcm;
    String regid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (checkPlayServices()) {//GOOGLE PLAY SERVÝCE APK YÜKLÜMÜ
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());//GoogleCloudMessaging objesi oluþturduk
            regid = getRegistrationId(getApplicationContext()); //registration_id olup olmadýðýný kontrol ediyoruz

            if(regid.isEmpty()){//YENÝ KAYIT
                //regid deðerimiz boþ gelmiþse uygulama ya ilk kez acýlýyor yada güncellenmiþ demektir.Registration iþlemleri tekrardan yapýlacak.
                new RegisterApp(getApplicationContext(), gcm, getAppVersion(getApplicationContext())).execute(); //RegisterApp clasýný çalýþtýrýyoruz ve deðerleri gönderiyoruz
            }else{
                //regid deðerimiz boþ gelmemiþse önceden registration iþlemleri tamamlanmýþ ve güncelleme olmamýþ demektir.Yani uygulama direk açýlacak
                //Arkadaþlar eðer splash ekranýnýn gözükmesini istiyorsanýz thread kullanýp 2 3 sn bekletebilirsiniz.Daha sonra aþaðýdaki iþlemlere baþlayabilirsiniz

                Toast.makeText(getApplicationContext(), "Bu cihaz önceden kaydedilmiþ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);//Anasayfaya Yönlendir
                startActivity(i);
                finish();
            }

        }

    }

    private boolean checkPlayServices() {
        //Google Play Servis APK yüklümü
        //Yüklü Deðilse Log basýp kapatýcak uygulamayý
        //Siz kullanýcýya uyarý verdirip Google Play Apk Kurmasýný isteyebilirsiniz

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "Google Play Servis Yükleyin.");
                finish();
            }
            return false;
        }
        return true;
    }
    private String getRegistrationId(Context context) { //registration_id geri döner
        //Bu method registration id ye bakar.
        //Bu uygulamada registration id nin önceden olabilmesi için uygulamanýn önceden açýlmýþ ve registration iþlemlerini yapmýþ olmasý lazým
        //Uygulama önceden acýldýysa registration_id SharedPreferences yardýmý ile kaydedilir.

        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");//registration_id deðeri alýndý
        if (registrationId.isEmpty()) {//eðer boþsa önceden kaydedilmemiþ yani uygulama ilk kez çalýþýyor.
            Log.i(TAG, "Registration id bulunamadı.");
            return "";
        }

        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(getApplicationContext());//yine SharedPreferences a kaydedilmiþ version deðerini aldýk
        if (registeredVersion != currentVersion) {//versionlar uyuþmuyorsa güncelleme olmuþ demektir. Yani tekrardan registration iþlemleri yapýlcak
            Log.i(TAG, "App version değişmiş.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(SplashScreenActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) { //Versiyonu geri döner
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Paket versiyonu bulunamadý: " + e);
        }
    }
}