package com.example.maekung.edu_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.model.TextSearchOptions;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static java.lang.Boolean.TRUE;

/**
 * Created by maekung on 12/1/17.
 */

public class loginActivity extends Activity implements View.OnClickListener {
    EditText id;
    EditText password;
    ArrayList<String> arrayList;
    File file;
    FileReader fr = null;
    FileWriter fw = null;
    BufferedReader br = null;
    BufferedWriter bw = null;
    int err_count = 0;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
//    String path = getApplicationContext().getFilesDir().getPath().toString() + "info.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button)findViewById(R.id.login);
        Button register = (Button)findViewById(R.id.register);
        id = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


        login.setOnClickListener(this);
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
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                find();
                break;
            case R.id.register:
                Intent intent = new Intent(this, registerActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void find(){
        int success =0;
       // init();
        //arrayList = new ArrayList<String>();
        try{
            bw = new BufferedWriter(new FileWriter(getFilesDir()+"info.txt", true));
            fr = new FileReader(getFilesDir()+"info.txt");

            br = new BufferedReader(fr);
            String s = null;
            String s2= null;
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, "EUC_KR"));
            while((s=br.readLine())!=null){
                s2=br.readLine();

                    if(s.equals(id.getText().toString())==TRUE && s2.equals(password.getText().toString()) == TRUE){
                        Toast.makeText(this, "User "+id.getText()+"님 환영합니다!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, bodyActivity.class);
                        intent.putExtra("id", id.getText().toString());
                        startActivity(intent);
                        finish();
                        success=1;
                        break;
                    }
            }
            err_count++;
            if(err_count==3 && success ==0){
                err_count = 0;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Login에 3회 실패하셨습니다.");
                builder.setMessage("회원 가입 하시겠습니까?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
            }
            else if(err_count !=3 && success==0) {
                Toast.makeText(this, "[" + err_count + "/3] Check your ID or Password", Toast.LENGTH_SHORT).show();
            }

        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();


        }


    }







/*
    public void find(){
        String ip = "127.0.0.1";
        int port = 27017;
        String DB_Name = "android";
        int check = 0;

        MongoClient mongoClient = new MongoClient(new ServerAddress(ip, port));
        DB db = mongoClient.getDB(DB_Name);
        DBCollection collection = db.getCollection("user_info");


        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("id", id.getText()));
        obj.add(new BasicDBObject("password", password.getText()));
        andQuery.put("$and", obj);
        DBCursor cursor = collection.find(andQuery);
        while (cursor.hasNext()) {
            check++;
        }

        if(check ==0){
            Toast.makeText(this, "WelCome!! User "+ id.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class); // 수정
            intent.putExtra("id", id.getText());
         //  startActivities(intent);
            finish();

        }
        else {
            Toast.makeText(this, "Not Registered", Toast.LENGTH_SHORT).show();
        }



    }
    */
}
