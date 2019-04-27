package com.example.a90531.medicheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class KisilerActivity extends AppCompatActivity implements AsyncResponse{
    String type="getdoctor";
    private ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    ArrayList<String> idler= new ArrayList<>();
    ArrayList<String> doktorlar= new ArrayList<>();
    ArrayList<String> olasiArananlarList;
    EditText etSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiler);
        listView=(ListView)findViewById(R.id.listDoctors);
        etSearch=(EditText)findViewById(R.id.etSearch);


        BackgroundWorker_class backgroundWorker_class = new BackgroundWorker_class(KisilerActivity.this);
        backgroundWorker_class.delegate=KisilerActivity.this;
        backgroundWorker_class.execute(type);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(KisilerActivity.this,ChatBoxActivity.class);
                i.putExtra("id",idler.get(position));
                startActivity(i);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(""))
                    initListView();
                else
                    searchItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void searchItem(String textToSearch){
        for (String item:doktorlar){
            if(!item.contains(textToSearch)){
                doktorlar.remove(item);
            }

        }
        adapter.notifyDataSetChanged();
    }

    public void initListView(){
        adapter=new ArrayAdapter<String>(this,R.layout.list_item2,R.id.txtitem,doktorlar);
        listView.setAdapter(adapter);
    }


    @Override
    public void processFinish(String output) {
        String dizi[]=output.trim().split("_");
        int sayac=0;
        String []a = new String[100];
        while (sayac<dizi.length-1){
            a=dizi[sayac].trim().split("--");
            idler.add(a[0]);
            doktorlar.add(a[1]);
            sayac++;
        }
        initListView();
    }
}
