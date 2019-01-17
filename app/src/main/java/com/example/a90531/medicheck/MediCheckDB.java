package com.example.a90531.medicheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MediCheckDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ilacDB";
    private static final int DATABASE_VERSION=1;
    private static final String TABLENAME_ALARM="alarmlar";
    private static final String COLOUMN_ALARMID="id";
    private static final String COLOUMN_ILACID="ilac_id";
    private static final String COLOUMN_MILLIS="millis";
    private static final String COLOUMN_KACALARM="kacAlarm";


    private static final String query_alarm="CREATE TABLE "+TABLENAME_ALARM+" ( "
            +COLOUMN_ALARMID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLOUMN_ILACID+" INTEGER NOT NULL, "
            +COLOUMN_KACALARM+" TEXT NOT NULL, "
            +COLOUMN_MILLIS+" TEXT)";


    private static final String TABLENAME_ALDINMI="aldin_mi";
    private static final String COLOUMN_ALDINMIID="id";
    private static final String COLOUMN_ALDINMIILACID="ilac_id";
    private static final String COLOUMN_ALDINMI="aldinmi_deger";


    private static final String query_aldinmi="CREATE TABLE "+TABLENAME_ALDINMI+" ( "
            +COLOUMN_ALDINMIID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLOUMN_ALDINMIILACID+" TEXT NOT NULL, "
            +COLOUMN_ALDINMI+" TEXT NOT NULL)";

    public MediCheckDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query_alarm);
        db.execSQL(query_aldinmi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryAlarm="DROP TABLE IF EXISTS "+TABLENAME_ALARM;
        db.execSQL(queryAlarm);
        String queryAldinmi="DROP TABLE IF EXISTS "+TABLENAME_ALDINMI;
        db.execSQL(queryAldinmi);
        onCreate(db);
    }





    public int alarmsSay(int gelenAlarmId){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor;
        cursor=DB.rawQuery("SELECT COUNT("+COLOUMN_ILACID+") FROM "+TABLENAME_ALARM+" WHERE "+COLOUMN_ILACID+" = "+gelenAlarmId,null);
        int kac = 0;
        if(cursor.moveToFirst()){
            kac=cursor.getInt(0);
        }
        return kac;
    }

    public ArrayList<String> DBArrayAlarm(){
        ArrayList<String> alarmlist = new ArrayList<>();

        SQLiteDatabase DB = this.getReadableDatabase();

        String alarmlar[]={COLOUMN_ALARMID,COLOUMN_ILACID,COLOUMN_MILLIS};
        Cursor cursor = DB.query(TABLENAME_ALARM,alarmlar,null, null, null, null, null);
        while (cursor.moveToNext()){
            alarmlist.add(cursor.getInt(0)
                    +"--"+cursor.getString(1)
                    +"--"+cursor.getString(2));
        }
        return alarmlist;
    }



    public void alarmDelete(long millis){
        SQLiteDatabase DB = this.getWritableDatabase();
        String where=COLOUMN_MILLIS+"="+millis;
        DB.delete(TABLENAME_ALARM,where,null);
        DB.close();
    }

    public void alarmDeleteById(int id){
        SQLiteDatabase DB = this.getWritableDatabase();
        String where=COLOUMN_ILACID+"="+id;
        DB.delete(TABLENAME_ALARM,where,null);
        DB.close();
    }




    //Veritabanından alarm bilgilerini çekemek için alarmListActivity'den
    //aldığım alarmId değerlerini bu metoda gönderip alarm değerlerini çektim.
    //alarm tablosunda ilac_id kısmı da mevcut bu şekilde UpdateDeleteAlarmActivity ekranında
    //istediğim şekilde listeledim.
    public int DBArrayHangiAlarm(long alarmMillis){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor;
        int hangi_alarm = 0;
        String m=String.valueOf(alarmMillis);
        cursor=DB.rawQuery("SELECT * FROM "+TABLENAME_ALARM+" WHERE "+COLOUMN_MILLIS+"='"+m+"'",null);
        if(cursor.moveToFirst()){
            hangi_alarm=cursor.getInt(0);
        }
        return hangi_alarm;
    }



    public ArrayList<String> DBArrayHangiIlacinAlarmIdleri(int ilacId){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor;
        ArrayList<String> hangi_ilac = new ArrayList<>();

        cursor=DB.rawQuery("SELECT "+COLOUMN_ALARMID+" FROM "+TABLENAME_ALARM+" WHERE "+COLOUMN_ILACID+"="+ilacId,null);
        while (cursor.moveToNext()){
            hangi_ilac.add(cursor.getString(0));
        }
        return hangi_ilac;
    }


}
