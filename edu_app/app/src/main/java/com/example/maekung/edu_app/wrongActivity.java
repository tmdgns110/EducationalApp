package com.example.maekung.edu_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by maekung on 12/6/17.
 */

public class wrongActivity extends Activity implements View.OnClickListener {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    String id;
    int grade=0;

    Button one;
    Button two;
    Button three;
    Button four;
    Button note;
    TextView q;
    int count =0;
    int answer_place =0;



    FileReader fr = null;
    FileWriter fw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    BufferedWriter bw2 = null;
    PrintWriter pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        one = (Button)findViewById(R.id.one);
        two = (Button)findViewById(R.id.two);
        three = (Button)findViewById(R.id.three);
        four = (Button)findViewById(R.id.four);

        q = (TextView)findViewById(R.id.q);

        String data = null;

        try{
            bw = new BufferedWriter(new FileWriter(getFilesDir()+"wrong.txt", true));

            fr = new FileReader(getFilesDir()+"wrong.txt");
            br = new BufferedReader(fr);
            String s = null;
            String s2= null;
            String s3 = null;
            StringBuffer stringBuffer = new StringBuffer();

            Random random = new Random();


            int q_number = 0;
            while(true){
                s=br.readLine();
                count++;


                while(!((s2=br.readLine()).equals("END"))){
                    stringBuffer.append(s2+"\n");

                }

                s3 = br.readLine();

                q_number=random.nextInt(count)+1;
                if(q_number == Integer.parseInt(s.toString()))break;
                else stringBuffer = new StringBuffer();

            }
            q.setText(stringBuffer);
            int answer = random.nextInt(3)+1;
            int dummy;

            if(answer == 1){
                one.setText(s3);
                dummy = random.nextInt(3)+1;
                two.setText(Integer.toString(dummy+Integer.parseInt(s3)));
                dummy = random.nextInt(5)+1;
                three.setText(Integer.toString(dummy-Integer.parseInt(s3)));
                dummy = random.nextInt(10)+1;
                four.setText(Integer.toString(dummy+Integer.parseInt(s3)));

                answer_place=1;
            }
            else if(answer ==2){
                dummy = random.nextInt(3)+1;
                one.setText(Integer.toString(dummy+Integer.parseInt(s3)));
                two.setText(s3);
                dummy = random.nextInt(10)+1;
                three.setText(Integer.toString(dummy-Integer.parseInt(s3)));
                dummy = random.nextInt(5)+1;
                four.setText(Integer.toString(dummy+Integer.parseInt(s3)));

                answer_place=2;
            }
            else if(answer ==3){
                dummy = random.nextInt(3)+1;
                one.setText(Integer.toString(dummy-Integer.parseInt(s3)));
                dummy = random.nextInt(10)+1;
                two.setText(Integer.toString(dummy+Integer.parseInt(s3)));
                three.setText(s3);
                dummy = random.nextInt(10)+1;
                four.setText(Integer.toString(dummy-Integer.parseInt(s3)));

                answer_place=3;
            }
            else{
                dummy = random.nextInt(4)+1;
                one.setText(Integer.toString(dummy+Integer.parseInt(s3)));
                dummy = random.nextInt(10)+1;
                two.setText(Integer.toString(dummy+Integer.parseInt(s3)));
                dummy = random.nextInt(6)+1;
                three.setText(Integer.toString(dummy-Integer.parseInt(s3)));
                four.setText(s3);

                answer_place=4;
            }


        }catch (Exception e){
            e.printStackTrace();
          // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
    }
    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
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
            Intent intent = new Intent(this,bodyActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
            finish();

        }

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.one:
                if(answer_place==1){
                    Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
                    onResume();

                }
                else{
                    Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();
                }


                break;
            case R.id.two:
                if(answer_place==2){
                    Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();


                }
                else{
                    Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();

                }


                break;
            case R.id.three:
                if(answer_place==3){

                    Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();

                }
                else{
                    Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();

                }


                break;

            case R.id.four:
                if(answer_place==4){

                    Toast.makeText(this, "정답입니다!", Toast.LENGTH_SHORT).show();
                    onResume();
                }
                else{
                    Toast.makeText(this, "오답입니다!", Toast.LENGTH_SHORT).show();

                    onResume();


                }


                break;

        }


    }
}
