package com.example.a90531.medicheck;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MessagesMainActivity extends AppCompatActivity {
    String []items;
    ArrayList<String> olasiArananlarList;
    ArrayAdapter<String> adapter;
    private ListView listView;
    EditText etSearch;
    TextView tx_mesaj_yok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_main);
        etSearch=(EditText)findViewById(R.id.etS);
        listView=(ListView)findViewById(R.id.listMessages);
        tx_mesaj_yok=(TextView)findViewById(R.id.tx_mesaj_yok);
        items=new String[]{""};

        if(!items[0].equals("")){
            tx_mesaj_yok.setVisibility(View.GONE);
        }
        initListView();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                switch (selectedItem){

                }
            }
        });

    }

    public void mesajyazClicked(View view){
        Toast.makeText(getApplicationContext(),"mesajyazClicked",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(MessagesMainActivity.this,KisilerActivity.class);
        startActivity(i);
    }

    public void initListView(){
        olasiArananlarList=new ArrayList<>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,olasiArananlarList);
        listView.setAdapter(adapter);
    }

    public void searchItem(String textToSearch){
        for (String item:items){
            if(!item.contains(textToSearch)){
                olasiArananlarList.remove(item);
            }

        }
        adapter.notifyDataSetChanged();
    }

}
