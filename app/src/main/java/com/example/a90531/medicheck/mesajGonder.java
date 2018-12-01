package com.example.a90531.medicheck;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class mesajGonder  extends AsyncTask<Void, Void, String> {
    private static final String TAG = "Mobilhanem GCM";
    Context ctx;
    GoogleCloudMessaging gcm;//Google Cloud referansı
    final String PROJECT_ID = "83042319627";//Bu değer Google Apı sayfasında Owerview menüsünde(Giriş sayfası) yukarıda yer alır. Project Number:987... şeklinde
    String regid = null;
    String mesaj;
    String gonderenId;
    String alanId;
    String date;



    public mesajGonder(Context ctx, GoogleCloudMessaging gcm,String mesaj, String gonderenId,String alanId,String date){ //SplassScreen den gelen değerleri aldık
        this.ctx = ctx;
        this.gcm = gcm;
        this.mesaj = mesaj;
        this.gonderenId=gonderenId;
        this.alanId=alanId;
        this.date=date;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(Void... arg0) { //
        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(ctx);//GCM objesi oluşturduk ve gcm referansına bağladık
            }
            regid = gcm.register(PROJECT_ID);//gcm objesine PROJECT_ID mizi göndererek regid değerimizi aldık.Bu değerimizi hem sunucularımıza göndereceğiz Hemde Androidde saklıyacağız
            msg = "Registration ID=" + regid;


            sendRegistrationIdToBackend();//Sunuculara regid gönderme işlemini yapacak method


        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();

        }
        return msg;
    }



    private void sendRegistrationIdToBackend() {//Sunucuya regid değerini gönderecek method
        //Arkadaşlar biz burda get methodu ile gönderdik .
        //Siz isterseniz post methoduda kullanabilirsiniz
        //HTTP Post ie ilgili dersimiz blog umuzda bulunmaktadır.
        URI url = null;
        try {
            String birlesikString=mesaj+"--"+gonderenId+"--"+alanId;
            String temp = ";asd";
            url = new URI("http://192.168.0.10/medicheck/medicheckDB/mesajgonder.php?deger="+birlesikString);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(url);
        try {
            httpclient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        //doInBackground işlemi bittikten sonra çalışır
        super.onPostExecute(result);
        Toast.makeText(ctx, "MEsaj gonderme işlemi Tamamlandı.", Toast.LENGTH_SHORT).show();
        Log.v(TAG, result);
        Intent i = new Intent(ctx,MessageActivity.class);//Anasayfaya Yönlendir
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        ctx.startActivity(i);

    }
}
