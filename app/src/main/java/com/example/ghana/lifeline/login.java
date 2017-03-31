package com.example.ghana.lifeline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.value;

public class login extends AppCompatActivity{
    String login;
    private Button log;
    private TextView tv,tv1,tv2;
    private EditText et,et1;
    private Spinner s;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        final Firebase root=new Firebase("https://lifeline-eb423.firebaseio.com/Donors");
        log=(Button)findViewById(R.id.login);
        tv=(TextView)findViewById(R.id.tv);
        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        et=(EditText)findViewById(R.id.et);
        et1=(EditText)findViewById(R.id.et1);
        s=(Spinner)findViewById(R.id.s);
        final String[] blood = new String[]{"Select One", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "A1+", "A1-", "A2+", "A2-", "A1B+", "A1B-", "A2B+", "A2B-"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(login.this, android.R.layout.simple_spinner_item, blood);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                login = blood[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s.setAdapter(adapter);


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String select=login;
                final String mobile=et.getText().toString().trim();
                final String password=et1.getText().toString().trim();
                if(!mobile.isEmpty() && !password.isEmpty() && !select.equals("Select One")){
                    Firebase ref=root.child(select);
                    ValueEventListener listener=new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            details user=dataSnapshot.getValue(details.class);
                            String name=user.username;
                            System.out.println(name);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("Read failed");

                        }
                    };


                    ref.child("abcd").addValueEventListener(listener);

                }
            }
        });

    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(login.this,regist.class);
                startActivity(intent);
            }
        });
        builder.show();
    }
}

