package com.example.example01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    private Button vm;
    private Button lb;
    private Button volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        main_add = (Button) findViewById(R.id.main_add);
//        textViewUserEmail = (TextView) findViewById(R.id.textviewUserEmail);
//        textViewUserUID = (TextView) findViewById(R.id.textviewUserUID);
        vm = (Button) findViewById(R.id.vm);
        lb = (Button) findViewById(R.id.lb);
        volume = (Button) findViewById(R.id.volume);
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

//        textViewUserEmail.setText(user.getEmail() + " 로그인 성공");
//        textViewUserUID.setText("UID : "+user.getUid());


        buttonLogout.setOnClickListener(this);
        main_add.setOnClickListener(this);
        vm.setOnClickListener(this);
        lb.setOnClickListener(this);
        volume.setOnClickListener(this);
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
        if (v == vm) {
            startActivity(new Intent(this, VMActivity.class));
        }
//        if (v == lb) {
//            startActivity(new Intent(this, LBActivity.class));
//        }
//        if (v == volume) {
//            startActivity(new Intent(this, VolumeActivity.class));
//        }
    }
}
