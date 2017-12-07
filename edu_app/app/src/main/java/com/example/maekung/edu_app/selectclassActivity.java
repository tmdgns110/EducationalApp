package com.example.maekung.edu_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;

import static java.lang.Boolean.TRUE;
import static java.lang.Integer.parseInt;

/**
 * Created by maekung on 12/6/17.
 */

public class selectclassActivity extends Activity implements View.OnClickListener{
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

    StringBuffer backup;

    FileReader fr = null;
    FileWriter fw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    BufferedWriter bw2 = null;
    PrintWriter pw;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selctclass);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        grade=intent.getIntExtra("grade",0);

        one = (Button)findViewById(R.id.one);
        two = (Button)findViewById(R.id.two);
        three = (Button)findViewById(R.id.three);
        four = (Button)findViewById(R.id.four);

        q = (TextView)findViewById(R.id.q);

        String data = null;
        InputStream inputStream=null;


        if(grade == 1) inputStream=getResources().openRawResource(R.raw.one);
        else if(grade == 2) inputStream=getResources().openRawResource(R.raw.two);
        else if(grade == 3) inputStream=getResources().openRawResource(R.raw.three);
        else if(grade == 4) inputStream=getResources().openRawResource(R.raw.four);
        else if(grade == 5) inputStream=getResources().openRawResource(R.raw.five);
        else if(grade == 6) inputStream=getResources().openRawResource(R.raw.six);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            bw = new BufferedWriter(new FileWriter(getFilesDir()+"question.txt", false));
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray(),"utf-8");
            bw.write(data);
            bw.flush();
            inputStream.close();

            fr = new FileReader(getFilesDir()+"question.txt");
            br = new BufferedReader(fr);
            String s = null;
            String s2= null;
            String s3 = null;
            StringBuffer stringBuffer = new StringBuffer();

            Random random = new Random();
            int q_number = random.nextInt(4)+1; // 1 ~ 5

            Log.e("data=",data);

            while(true){
                backup = new StringBuffer();
                s=br.readLine();

                while(!((s2=br.readLine()).equals("END"))){
                        stringBuffer.append(s2+"\n");
                        backup.append(s2+"\n");
                }
                backup.append("END\n");
                    s3 = br.readLine();
                backup.append(s3+"\n");
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


        } catch (IOException e) {
            e.printStackTrace();
            Log.e("wow: ", e.toString());
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

    public void save(){
        try {
            bw = new BufferedWriter(new FileWriter(getFilesDir() + "wrong.txt", true));

            count++;
            pw = new PrintWriter(bw, true);
            pw.write(Integer.toString(count) +"\n"+ backup.toString());
            pw.flush();
            pw.close();
            bw.close();

        }catch (Exception e){
            e.printStackTrace();
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
                    save();
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
                    save();
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
                    save();
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
                    save();
                    onResume();


                }


                break;

        }


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

}
