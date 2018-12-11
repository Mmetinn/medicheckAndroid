package com.example.a90531.medicheck;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainPageActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavigationView nv;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        nv=(NavigationView)findViewById(R.id.naviMain);
        nv.setItemIconTintList(null);
        nv.bringToFront();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id){
                    case R.id.olcumGir:
                        Intent intent = new Intent(MainPageActivity.this,olcumKaydetActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.randevu:
                        Intent intent2 = new Intent(MainPageActivity.this,HastaneRandevuActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.randevu_list:
                        Intent intent3 = new Intent(MainPageActivity.this,randevuListActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.ilac_kaydet:
                        Intent intent4 = new Intent(MainPageActivity.this,ilacKaydetActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.ilac_listele:
                        Intent intent5 = new Intent(MainPageActivity.this,ilacListeActivity.class);
                        startActivity(intent5);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.acil_numara:
                        Intent intent6 = new Intent(MainPageActivity.this,acilNumberActivity.class);
                        startActivity(intent6);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                }
                //drawerLayout.closeDrawer();
                return true;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.message_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_my_contacts:
                Intent i = new Intent(MainPageActivity.this,MessagesMainActivity.class);
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



}
