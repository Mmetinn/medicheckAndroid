package com.example.a90531.medicheck;

import android.content.Context;
import android.content.SharedPreferences;

public class preferenceManager {

    Context context;
    SharedPreferences sharedPreferences;

    public preferenceManager(Context context) {
        this.context = context;
        getSharedPreferences();
    }

    public void getSharedPreferences() {
        sharedPreferences=context.getSharedPreferences(context.getString(R.string.my_preference),Context.MODE_PRIVATE);
    }

    public void writePreference(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.my_preference_key),"INIT_OK");
        editor.commit();
    }

    public boolean checkPreference(){
        boolean status= false;
        if (sharedPreferences.getString(context.getString(R.string.my_preference_key),"null").equals("null")){
            status=false;
        }else {
            status=true;
        }
        return status;
    }

    public void clearPreference(){
        sharedPreferences.edit().clear().commit();
    }
}
