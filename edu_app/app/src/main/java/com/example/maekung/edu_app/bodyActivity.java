package com.example.maekung.edu_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by maekung on 12/6/17.
 */

public class bodyActivity extends Activity implements View.OnClickListener{
   String id;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        TextView info = (TextView)findViewById(R.id.info);
        info.setText("User "+ id + "님 환영합니다");

        Button one = (Button)findViewById(R.id.yes);
        Button two = (Button)findViewById(R.id.no);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.yes:
                select();
                break;
            case R.id.no:
                Intent intent = new Intent(this, wrongActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
                break;

        }
    }

    public void select(){
        final CharSequence[] grade = {"1학년", "2학년", "3학년","4학년","5학년","6학년"};
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);


        alt_bld.setTitle("학년을 선택하세요");
        alt_bld.setSingleChoiceItems(grade, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                //Toast.makeText(getApplicationContext(), "Phone Model = "+grade[item], Toast.LENGTH_SHORT).show();

                int grade2 =0;
                if(grade[item].toString().equals("1학년")==true) grade2=1;
                else if(grade[item].toString().equals("2학년")==true) grade2=2;
                else if(grade[item].toString().equals("3학년")==true) grade2=3;
                else if(grade[item].toString().equals("4학년")==true) grade2=4;
                else if(grade[item].toString().equals("5학년")==true) grade2=5;
                else if(grade[item].toString().equals("6학년")==true) grade2=6;


                Intent intent = new Intent(bodyActivity.this, selectclassActivity.class);
                intent.putExtra("grade", grade2);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alert = alt_bld.create();
        alert.show();





    }
}
