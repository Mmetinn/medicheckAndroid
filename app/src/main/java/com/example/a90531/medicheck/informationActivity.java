package com.example.a90531.medicheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class informationActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager vPager ;
    private int layouts[]={R.layout.information_first,R.layout.information_second,R.layout.information_third,R.layout.information_fourth};
    private viewPagerAdapter viewPagerAdapter;
    private LinearLayout Dots_layout;
    private ImageView[]dots;
    private Button bnNext,bnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        if(new preferenceManager(this).checkPreference()){
            loadHome();
        }

        vPager=(ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter=new viewPagerAdapter(layouts,this);
        vPager.setAdapter(viewPagerAdapter);
        Dots_layout=(LinearLayout)findViewById(R.id.dotsLayout);
        bnNext=(Button)findViewById(R.id.bnNext);
        bnSkip=(Button)findViewById(R.id.bnSkip);
        bnNext.setOnClickListener(this);
        bnSkip.setOnClickListener(this);

        createDota(0);
        vPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDota(position);
                if(position==layouts.length-1){
                    bnNext.setText("Başla");
                    bnSkip.setVisibility(View.INVISIBLE);
                }else{
                    bnNext.setText("Sonraki");
                    bnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void createDota(int current_position){
        if(Dots_layout!=null){
            Dots_layout.removeAllViews();
        }
        dots=new ImageView[layouts.length];
        for(int i = 0 ; i< layouts.length;i++){
            dots[i]=new ImageView(this);
            if(i==current_position){
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }else{
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            Dots_layout.addView(dots[i],params);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bnNext:
                loadNextSlider();
                break;
            case R.id.bnSkip:
                loadHome();
                new preferenceManager(this).writePreference();
                break;
        }
    }
    private void loadHome(){
        //startActivity(new Intent(this,girisActivity.class));
        Dots_layout.setVisibility(View.GONE);
        vPager.setVisibility(View.GONE);
        bnNext.setVisibility(View.GONE);
        bnSkip.setVisibility(View.GONE);
       // finish();
    }
    private void loadNextSlider(){
        int next_slider=vPager.getCurrentItem()+1;
        if(next_slider<layouts.length){
            vPager.setCurrentItem(next_slider);
         }else {
            loadHome();
            new preferenceManager(this).writePreference();
        }
    }

    public void baslaClicked(View v){
        Intent intent = new Intent(informationActivity.this,LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
