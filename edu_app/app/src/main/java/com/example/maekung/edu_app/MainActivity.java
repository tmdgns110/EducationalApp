package com.example.maekung.edu_app;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.entire);

        linearLayout.setOnClickListener(this);

        ImageView plus = (ImageView)findViewById(R.id.plus);
        ImageView minus = (ImageView)findViewById(R.id.minus);
        ImageView multiply = (ImageView)findViewById(R.id.multiply);
        ImageView divide = (ImageView)findViewById(R.id.divide);

        plus.setImageResource(R.drawable.plus);
        minus.setImageResource(R.drawable.minus);
        multiply.setImageResource(R.drawable.multiply);
        divide.setImageResource(R.drawable.divide);


        TextView myText = (TextView) findViewById(R.id.intro);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(50); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        myText.startAnimation(anim);
    }

    @Override
    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }

    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.entire:
                Intent intent = new Intent(this, loginActivity.class);
                startActivity(intent);
                finish();

        }
    }

}
