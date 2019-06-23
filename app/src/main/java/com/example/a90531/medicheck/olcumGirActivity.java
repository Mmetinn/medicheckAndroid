package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import android.os.Message;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class olcumGirActivity extends AppCompatActivity  implements AsyncResponse{
    private LinearLayout linnear;
    private EditText etDeger,etAciklama;
    private String deger,aciklama;
    private String hangiOlcum=null;
    private String userId;
    private SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMM");
    private SimpleDateFormat dateFormatTx= new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String date = dateFormatTx.format(new Date().getTime());
    private String []array= new String[400];
    private ArrayList<String> olcumlerList = new ArrayList<>();
    LineGraphSeries<DataPoint> series;
    GraphView graphView;
    String dizi[];
    private String hangiOlcumdizi[]=new String[100];
    private int measumentSize=0;


    ProgressDialog progressDoalog;
    String []headres={"kilo","aciklama","olcum tarihi"};
    String shortText="Merhaba,";
    String longText="asagidaki tabloda kaydedilen kilo olcum sonuclarım, olcum saatleri olcum aciklarmalarim ve bir grafik mevcuttur.\n\n";
    String longTextTesekkur="\n\n\n\nilac saati hatirlatici kullandiginiz icin tesekkur ederiz...";
    templatePDF templatePdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olcum_gir);
        linnear=(LinearLayout)findViewById(R.id.linList);
        etDeger = (EditText)findViewById(R.id.degerEt);
        etAciklama = (EditText)findViewById(R.id.aciklamaEt);
        graphView = (GraphView) findViewById(R.id.grafik);


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


        Toast.makeText(getApplicationContext(),"olcum degeri--> "+hangiOlcum,Toast.LENGTH_SHORT).show();
    }

    public void btnClicked(View view){
        aciklama=etAciklama.getText().toString();
        deger = etDeger.getText().toString();
        createCard();
    }

    private void createCardAll() {
        int sayac=0;
        measumentSize=olcumlerList.size();
        while (sayac<olcumlerList.size()){
            dizi=olcumlerList.get(sayac).split("--");
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
            hangiOlcumdizi[sayac]=dizi[3];
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
        if(!output.trim().equals("insert successful")&&!output.trim().equals("")) {
            createCardAll();
            grafik();

        }
    }

    public void grafik(){
        series = new LineGraphSeries<DataPoint>(getDataPoint());
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return dateFormat.format(new Date((long) value));
                } else
                    return super.formatLabel(value, isValueX);
            }
        });
        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(2);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                String mesaj = dataPoint.getY() + " mmHg";
                Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
            }
        });
        series.setDrawBackground(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(6);
        /*series.setTitle("mmHg");
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setTextSize(30);
        graphView.getLegendRenderer().setTextColor(Color.WHITE);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);*/
        graphView.addSeries(series);
    }
    private DataPoint[] getDataPoint(){
        //ilacSaatiDB DB = new ilacSaatiDB(this);

        int array_counter=0;
        int z=0;
        int hangiOlcumSize=0;
        while (z< measumentSize){
            if(hangiOlcumdizi[z].equals(hangiOlcum)) {
                hangiOlcumSize++;
            }
            z++;
        }
        DataPoint []dataPoint = new DataPoint[hangiOlcumSize];//=new DataPoint[1];
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date startDate = null;

        int sayac=0;
        measumentSize=olcumlerList.size();
        while (sayac<olcumlerList.size()){
            dizi=olcumlerList.get(sayac).split("--");
            if(dizi[3].equals(hangiOlcum)) {
                try {
                    startDate = df.parse(dizi[5]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int dataPointCounter=0;
                while (array_counter<measumentSize) {
                    if(hangiOlcumdizi[array_counter].equals(hangiOlcum)) {
                        dataPoint[dataPointCounter] = new DataPoint(startDate, Long.parseLong(dizi[1]));
                        dataPointCounter++;
                    }
                    array_counter++;
                }
            }
            hangiOlcumdizi[sayac]=dizi[3];
            sayac++;

        }

        return dataPoint;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void pdfCreateClicked(View view){
        olcumGirActivity.LoginProgressTask asyn = new olcumGirActivity.LoginProgressTask();
        asyn.execute();
        // graphView.takeSnapshotAndShare(kanSekeriActivity.this, "exampleGraph", "GraphViewSnapshot");

        Bitmap bitmap = graphView.takeSnapshot();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
        Image myImg = null;
        try {
            myImg = Image.getInstance(stream.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myImg.setAlignment(Image.MIDDLE);
        templatePdf=new templatePDF(getApplicationContext());
        templatePdf.openDocument();
        templatePdf.addMetaData("Clientes","Ventas","Marines");
        templatePdf.addTitle("medicheck","Grafik",dateFormatTx.format(new Date().getTime()));
        templatePdf.addParagraph(shortText);
        templatePdf.addParagraph(longText);
        templatePdf.addSingleTitle("Grafik");
        templatePdf.addImage(myImg);
        templatePdf.addSingleTitle("Ölçüm Tablosu");
        templatePdf.createTable(headres,getClients());
        templatePdf.addParagraph(longTextTesekkur);
        templatePdf.closeDocument();
    }

    Handler handle = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDoalog.incrementProgressBy(1);
        }
    };
    class LoginProgressTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }
        @Override
        protected void onPreExecute() {
            progressDoalog = new ProgressDialog(olcumGirActivity.this);
            progressDoalog.setMax(100);
            progressDoalog.setMessage("Yükleniyor");
            progressDoalog.setTitle("PDF oluşturuluyor.");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDoalog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (progressDoalog.getProgress() <= progressDoalog
                                .getMax()) {
                            Thread.sleep(50);
                            handle.sendMessage(handle.obtainMessage());
                            if (progressDoalog.getProgress() == progressDoalog
                                    .getMax()) {
                                progressDoalog.dismiss();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        @Override
        protected void onPostExecute(Boolean result) {
            // result is the value returned from doInBackground
            progressDoalog.cancel();
            templatePdf.viewPDF();
        }
    }
    private ArrayList<String[]>getClients(){
        ArrayList<String>temp=new ArrayList<>();
        ArrayList<String []>rows=new ArrayList<>();
        int sayac = 0;
        if(temp.size()==0){
            rows.add(new String[]{"Deger yok","Deger yok","Deger yok"});
            return rows;
        }

        while (sayac<temp.size()){
            rows.add(new String[]{dizi[1],dizi[2],dizi[5]});
            sayac++;
        }
        return rows;
    }

}
