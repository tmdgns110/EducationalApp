package com.example.maekung.edu_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maekung on 12/1/17.
 */

public class loginActivity extends Activity implements View.OnClickListener {
    EditText id;
    EditText password;

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

    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                find();
                break;
            case R.id.register:

                break;
        }
    }

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
}
