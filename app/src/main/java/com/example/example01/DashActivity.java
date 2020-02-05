package com.example.example01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DashActivity";

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private TextView textViewUserUID;
    private Button buttonLogout;
    private Button main_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        main_add = (Button) findViewById(R.id.main_add);
        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
        textViewUserUID = (TextView) findViewById(R.id.textviewUserUID);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail.setText(user.getEmail() + " 로그인 성공");
        textViewUserUID.setText("UID : "+user.getUid());


        buttonLogout.setOnClickListener(this);
        main_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (v == main_add) {
            startActivity(new Intent(this, AddActivity.class));
        }
    }
}
