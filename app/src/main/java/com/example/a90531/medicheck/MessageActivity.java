package com.example.a90531.medicheck;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageActivity extends AppCompatActivity  implements AsyncResponse
{
    String gonderenId,alanId;
    final String PROJECT_ID = "83042319627";
    String userId= "";
    Button btnSend;
    EditText etMessage;
    private String user_id;
    private String message;
    private String type, username,password;
    LinearLayout llMessages;
    LinearLayout.LayoutParams lp;
    String get_messages="";
    String device_type="a";
    SimpleDateFormat dateFormat= new SimpleDateFormat("MM-dd HH:mm:ss");
    String date=dateFormat.format(new Date().getTime());
    String mesaj="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        btnSend=(Button)findViewById(R.id.messageBtn);
        etMessage=(EditText)findViewById(R.id.messageEt);
        llMessages=(LinearLayout)findViewById(R.id.messageLinear);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                alanId= null;
            } else {
                alanId= extras.getString("id");
            }
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId=sharedPreferences.getString("userId","null");
        Toast.makeText(getApplicationContext(),alanId+" hastanın idsi ise "+userId,Toast.LENGTH_SHORT).show();
    }
    public void sendClicked(View view){
        mesaj=etMessage.getText().toString();
       /* gcm = GoogleCloudMessaging.getInstance(getApplicationContext());//GoogleCloudMessaging objesi oluşturduk
        new mesajGonder(this,gcm,mesaj,alanId,userId,date).execute();*/

        String type="messagesend";
        BackgroundWorker_class backgroundWorker_class2 = new BackgroundWorker_class(this);
        backgroundWorker_class2.delegate=MessageActivity.this;
        backgroundWorker_class2.execute(type,mesaj,alanId,userId,date);
        Toast.makeText(getApplicationContext(),"Mesaj gönderildi",Toast.LENGTH_SHORT).show();
    }


    public void messageList(){
        String []dizi=get_messages.trim().split("--");
        int sayac=0;
        while (sayac<dizi.length && dizi[0].equals(" ")){
            String []dizi_= dizi[sayac].split("-");
            TextView tx = new TextView(MessageActivity.this);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if(dizi_[1].equals("a") ){
                lp.gravity=Gravity.RIGHT;
                tx.setLayoutParams(lp);
                tx.setGravity(Gravity.RIGHT);
            }else if(dizi_[1].equals("w")){
                lp.gravity=Gravity.LEFT;
                tx.setLayoutParams(lp);
                tx.setGravity(Gravity.LEFT);
            }
            tx.setText(dizi_[0]);
            llMessages.addView(tx,lp);
            sayac++;
        }
    }

    @Override
    public void processFinish(String output) {

    }
}
