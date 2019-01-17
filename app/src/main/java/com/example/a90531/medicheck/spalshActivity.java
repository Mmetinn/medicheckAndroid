package com.example.a90531.medicheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class spalshActivity extends AppCompatActivity {
    ImageView ivBallon,ivHand;
    Animation fromBottom,fromTop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        ivBallon=(ImageView)findViewById(R.id.balloniv);
        ivHand=(ImageView)findViewById(R.id.handiv);

        fromBottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        fromTop = AnimationUtils.loadAnimation(this,R.anim.fromtop);
        ivHand.setAnimation(fromBottom);
        ivBallon.setAnimation(fromTop);
        final Intent i = new Intent(this,LoginActivity.class);
        Thread timer = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            }
        };
        timer.start();
    }
}
