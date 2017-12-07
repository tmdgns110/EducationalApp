package com.example.maekung.edu_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import static java.lang.Boolean.TRUE;

/**
 * Created by maekung on 12/6/17.
 */

public class registerActivity extends Activity implements View.OnClickListener {
    Boolean id_checker = false;
    EditText id;
    EditText password;
    FileReader fr = null;
    FileWriter fw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    PrintWriter pw;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        Button checker = (Button)findViewById(R.id.check);
        Button register = (Button)findViewById(R.id.register);
        id = (EditText)findViewById(R.id.id);
        password = (EditText)findViewById(R.id.password);

        checker.setOnClickListener(this);
        register.setOnClickListener(this);

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
            Intent intent = new Intent(this, loginActivity.class);
            startActivity(intent);
            finish();

//            backPressedTime = tempTime;
//            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }

    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.check:
               checker();
                break;
            case R.id.register:
                register();
                break;
        }
    }

    public void register(){
       if(id_checker == true) {

           pw = new PrintWriter(bw, true);
           pw.write(id.getText().toString() + "\n" + password.getText().toString() + "\n");
           pw.flush();
           pw.close();

           Intent intent = new Intent(this, loginActivity.class);
           startActivity(intent);
           finish();
       }

       else{
           Toast.makeText(this, "Please ID check first", Toast.LENGTH_SHORT).show();
       }

    }


    public void checker(){
        try{
            bw = new BufferedWriter(new FileWriter(getFilesDir()+"info.txt", true));
            fr = new FileReader(getFilesDir()+"info.txt");

            br = new BufferedReader(fr);
            String s = null;
            String s2= null;
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, "EUC_KR"));
            while((s=br.readLine())!=null){
                s2=br.readLine();

                if(s.equals(id.getText().toString())==TRUE ){
                    Toast.makeText(this, id.getText()+"는 이미 존재합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        Toast.makeText(this, id.getText()+"는 사용 가능합니다.", Toast.LENGTH_SHORT).show();
        id_checker=true;

    }

}
