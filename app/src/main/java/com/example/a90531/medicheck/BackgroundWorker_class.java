package com.example.a90531.medicheck;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker_class extends AsyncTask<String,String ,String > {
    Context context;
    AlertDialog alertDialog;
    public AsyncResponse delegate = null;
    GoogleCloudMessaging gcm=null;
    final String PROJECT_ID = "83042319627";
    public BackgroundWorker_class(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... params) {
        String type=params[0];

        /*
        *
        *
        * //kingsuit
        String registeredUrl="http://192.168.0.10/medicheck/medicheckDB/registeredHasta.php";
        String loginUrl="http://192.168.0.10/medicheck/medicheckDB/login.php";
        String getdoctorsnUrl="http://192.168.0.10/medicheck/medicheckDB/getdoctors.php";
        String messagesendUrl="http://192.168.0.10/medicheck/medicheckDB/mesajgonder.php";
        String olcumkaydetUrl="http://192.168.0.10/medicheck/medicheckDB/olcumkaydet.php";
        String getolcumlerUrl="http://192.168.0.10/medicheck/medicheckDB/getolcumler.php";
        String getkayitlidoktorlarUrl="http://192.168.0.10/medicheck/medicheckDB/getdoktorhasta.php";
        String getpatientsDoctors="http://192.168.0.10/medicheck/medicheckDB/patientsDoctors.php";
        String setAppointmetUrl="http://192.168.0.10/medicheck/medicheckDB/setAppointmet.php";
        String listAppointmetUrl="http://192.168.0.10/medicheck/medicheckDB/listAppointment.php";
        String registerMedicUrl="http://192.168.0.10/medicheck/medicheckDB/registerMedic.php";
        String listMedicUrl="http://192.168.0.10/medicheck/medicheckDB/listMedic.php";
        String hastaDoktorKayitUrl="http://192.168.0.10/medicheck/medicheckDB/doktrHastaKayit.php";
        */

        //meto
        String registeredUrl="http://192.168.43.247/medicheck/medicheckDB/registeredHasta.php";
        String loginUrl="http://192.168.43.247/medicheck/medicheckDB/login.php";
        String getdoctorsnUrl="http://192.168.43.247/medicheck/medicheckDB/getdoctors.php";
        String messagesendUrl="http://192.168.43.247/medicheck/medicheckDB/mesajgonder.php";
        String olcumkaydetUrl="http://192.168.43.247/medicheck/medicheckDB/olcumkaydet.php";
        String getolcumlerUrl="http://192.168.43.247/medicheck/medicheckDB/getolcumler.php";
        String getkayitlidoktorlarUrl="http://192.168.43.247/medicheck/medicheckDB/getdoktorhasta.php";
        String getpatientsDoctors="http://192.168.43.247/medicheck/medicheckDB/patientsDoctors.php";
        String setAppointmetUrl="http://192.168.43.247/medicheck/medicheckDB/setAppointmet.php";
        String listAppointmetUrl="http://192.168.43.247/medicheck/medicheckDB/listAppointment.php";
        String registerMedicUrl="http://192.168.43.247/medicheck/medicheckDB/registerMedic.php";
        String listMedicUrl="http://192.168.43.247/medicheck/medicheckDB/listMedic.php";
        String hastaDoktorKayitUrl="http://192.168.43.247/medicheck/medicheckDB/doktrHastaKayit.php";

        //neu
       /* String registeredUrl="http://169.254.153.254/medicheck/medicheckDB/registeredHasta.php";
        String loginUrl="http://169.254.153.254/medicheck/medicheckDB/login.php";
        String getdoctorsnUrl="http://169.254.153.254/medicheck/medicheckDB/getdoctors.php";
        String messagesendUrl="http://169.254.153.254/medicheck/medicheckDB/mesajgonder.php";
        String olcumkaydetUrl="http://169.254.153.254/medicheck/medicheckDB/olcumkaydet.php";
        String getolcumlerUrl="http://169.254.153.254/medicheck/medicheckDB/getolcumler.php";
        String getkayitlidoktorlarUrl="http://169.254.153.254/medicheck/medicheckDB/getdoktorhasta.php";
        String getpatientsDoctors="http://169.254.153.254/medicheck/medicheckDB/patientsDoctors.php";
        String setAppointmetUrl="http://169.254.153.254/medicheck/medicheckDB/setAppointmet.php";
        String listAppointmetUrl="http://169.254.153.254/medicheck/medicheckDB/listAppointment.php";
        String registerMedicUrl="http://169.254.153.254/medicheck/medicheckDB/registerMedic.php";
        String listMedicUrl="http://169.254.153.254/medicheck/medicheckDB/listMedic.php";
        String hastaDoktorKayitUrl="http://169.254.153.254/medicheck/medicheckDB/doktrHastaKayit.php";*/
       /*
       * neu ubuntu
       * ip:10.31.8.145*/
       /*
        String registeredUrl="http://10.31.8.145/medicheck/medicheckDB/registeredHasta.php";
        String loginUrl="http://10.31.8.145/medicheck/medicheckDB/login.php";
        String getdoctorsnUrl="http://10.31.8.145/medicheck/medicheckDB/getdoctors.php";
        String messagesendUrl="http://10.31.8.145/medicheck/medicheckDB/mesajgonder.php";
        String olcumkaydetUrl="http://10.31.8.145/medicheck/medicheckDB/olcumkaydet.php";
        String getolcumlerUrl="http://10.31.8.145/medicheck/medicheckDB/getolcumler.php";
        String getkayitlidoktorlarUrl="http://10.31.8.145/medicheck/medicheckDB/getdoktorhasta.php";
        String getpatientsDoctors="http://10.31.8.145/medicheck/medicheckDB/patientsDoctors.php";
        String setAppointmetUrl="http://10.31.8.145/medicheck/medicheckDB/setAppointmet.php";
        String listAppointmetUrl="http://10.31.8.145/medicheck/medicheckDB/listAppointment.php";
        String registerMedicUrl="http://10.31.8.145/medicheck/medicheckDB/registerMedic.php";
        String listMedicUrl="http://10.31.8.145/medicheck/medicheckDB/listMedic.php";
        String hastaDoktorKayitUrl="http://10.31.8.145/medicheck/medicheckDB/doktrHastaKayit.php";
*/
        if(gcm==null) {
            gcm = GoogleCloudMessaging.getInstance(context);//GoogleCloudMessaging objesi oluşturduk
        }
        String gcmString = "";
        try {
            gcmString ="Registration ID="+gcm.register(PROJECT_ID);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(type.equals("registered")){
            try {
                String user_name=params[1];
                String password=params[2];
                String adi=params[3];
                String soyadi=params[4];
                String email=params[5];
                String telefon=params[6];
                String tc=params[7];
                String hastalik=params[8];
                String tarih=params[9];
                String cinsiyet=params[10];
                String profilePic=params[11];

                URL url = new URL(registeredUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("adi","UTF-8")+"="+URLEncoder.encode(adi,"UTF-8")+"&"
                        +URLEncoder.encode("soyadi","UTF-8")+"="+URLEncoder.encode(soyadi,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("telefon","UTF-8")+"="+URLEncoder.encode(telefon,"UTF-8")+"&"
                        +URLEncoder.encode("hastalik","UTF-8")+"="+URLEncoder.encode(hastalik,"UTF-8")+"&"
                        +URLEncoder.encode("tarih","UTF-8")+"="+URLEncoder.encode(tarih,"UTF-8")+"&"
                        +URLEncoder.encode("cinsiyet","UTF-8")+"="+URLEncoder.encode(cinsiyet,"UTF-8")+"&"
                        +URLEncoder.encode("profilepic","UTF-8")+"="+URLEncoder.encode(profilePic,"UTF-8")+"&"
                        +URLEncoder.encode("tc","UTF-8")+"="+URLEncoder.encode(tc,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("login")){
            try {
                String user_name=params[1];
                String password=params[2];
                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("getdoctor")){
            try {
                URL url = new URL(getdoctorsnUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("messagesend")){
            try {
                String mesaj=params[1];
                String alanId=params[2];
                String userId=params[3];
                String date=params[4];

                URL url = new URL(messagesendUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("gcm","UTF-8")+"="+URLEncoder.encode(gcmString,"UTF-8")+"&"
                        +URLEncoder.encode("mesaj","UTF-8")+"="+URLEncoder.encode(mesaj,"UTF-8")+"&"
                        +URLEncoder.encode("alanId","UTF-8")+"="+URLEncoder.encode(alanId,"UTF-8")+"&"
                        +URLEncoder.encode("userId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("olcumgir")){
            try {
                String deger=params[1];
                String aciklama=params[2];
                String hangiOlcum=params[3];
                String hastaId=params[4];
                String date=params[5];

                Log.d("degerler",deger+aciklama);
                URL url = new URL(olcumkaydetUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("deger","UTF-8")+"="+URLEncoder.encode(deger,"UTF-8")+"&"
                        +URLEncoder.encode("aciklama","UTF-8")+"="+URLEncoder.encode(aciklama,"UTF-8")+"&"
                        +URLEncoder.encode("hangiOlcum","UTF-8")+"="+URLEncoder.encode(hangiOlcum,"UTF-8")+"&"
                        +URLEncoder.encode("hastaId","UTF-8")+"="+URLEncoder.encode(hastaId,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("getolcumler")){
            String hastaId=params[1];
            try {
                URL url = new URL(getolcumlerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hastaId","UTF-8")+"="+URLEncoder.encode(hastaId,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("getkayitlidoktorlar")){
            try {
                URL url = new URL(getkayitlidoktorlarUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("getPatientsDoctors")){
            String hastaId=params[1];
            try {
                URL url = new URL(getpatientsDoctors);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hastaId","UTF-8")+"="+URLEncoder.encode(hastaId,"UTF-8");
                bufferedWriter.write(postData);

                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("registerAppointment")){
            try {
                String saat=params[1];
                String tarih=params[2];
                String hasta_id=params[3];
                String doktor_id=params[4];
                String appo_date=tarih+" "+saat;

                URL url = new URL(setAppointmetUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(hasta_id,"UTF-8")+"&"
                        +URLEncoder.encode("doktor_id","UTF-8")+"="+URLEncoder.encode(doktor_id,"UTF-8")+"&"
                        +URLEncoder.encode("appo_date","UTF-8")+"="+URLEncoder.encode(appo_date,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("listAppointment")){
            try {
                String hasta_id=params[1];

                URL url = new URL(listAppointmetUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(hasta_id,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("registerMedic")){
            try {
                String userId=params[1];
                String ilac_adi=params[2];
                String ilac_aciklama=params[3];
                String ilac_actok=params[4];
                String ilac_kacdefa=params[5];
                String ilac_sure=params[6];
                String dataStr=params[7];

                URL url = new URL(registerMedicUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("ilac_adi","UTF-8")+"="+URLEncoder.encode(ilac_adi,"UTF-8")+"&"
                        +URLEncoder.encode("ilac_aciklama","UTF-8")+"="+URLEncoder.encode(ilac_aciklama,"UTF-8")+"&"
                        +URLEncoder.encode("ilac_actok","UTF-8")+"="+URLEncoder.encode(ilac_actok,"UTF-8")+"&"
                        +URLEncoder.encode("ilac_kacdefa","UTF-8")+"="+URLEncoder.encode(ilac_kacdefa,"UTF-8")+"&"
                        +URLEncoder.encode("ilac_sure","UTF-8")+"="+URLEncoder.encode(ilac_sure,"UTF-8")+"&"
                        +URLEncoder.encode("dataStr","UTF-8")+"="+URLEncoder.encode(dataStr,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("listMedic")){
            try {
                String userId=params[1];

                URL url = new URL(listMedicUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("hastaDoktorKayit")){
            try {
                String userId=params[1];
                String doktorId=params[2];

                URL url = new URL(hastaDoktorKayitUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("doktorId","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(doktorId,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }/*else if(type.equals("register_doctor_for_patient")){
            try {
                String userId=params[1];
                String doktorId=params[2];

                URL url = new URL(registerDoctorForPatient);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String postData = URLEncoder.encode("hasta_id","UTF-8")+"="+URLEncoder.encode(userId,"UTF-8")+"&"
                        +URLEncoder.encode("doktorId","UTF-8")+"="+URLEncoder.encode(doktorId,"UTF-8");
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while ((line = bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Giriş Durumu");
    }

    @Override
    protected void onPostExecute(String result) {
        if(result!=""){
            delegate.processFinish(result);
        }

    }
}
