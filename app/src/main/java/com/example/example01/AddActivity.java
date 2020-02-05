package com.example.example01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference mDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    KT_key_FragmentActivity frag_kt;
    AWS_key_FragmentActivity frag_aws;
    Azure_key_FragmentActivity frag_azure;

    Button KT;
    Button AWS;
    Button Azure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        frag_kt = new KT_key_FragmentActivity();
        frag_aws = new AWS_key_FragmentActivity();
        frag_azure = new Azure_key_FragmentActivity();

        KT = (Button)findViewById(R.id.KT);
        AWS = (Button)findViewById(R.id.AWS);
        Azure = (Button)findViewById(R.id.Azure);

        //아이디 정의
//        Button add_apply = (Button) findViewById(R.id.add_apply);
//        final EditText add_apikey = (EditText) findViewById(R.id.add_apikey);
//        final EditText add_secretkey = (EditText) findViewById(R.id.add_secretkey);

        firebaseAuth = FirebaseAuth.getInstance();

        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        //add_apply.setOnClickListener(this);
        KT.setOnClickListener(this);
        AWS.setOnClickListener(this);
        Azure.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == KT) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,frag_kt).commit();
        }
        if(v == AWS) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,frag_aws).commit();
        }
        if(v == Azure) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,frag_azure).commit();
        }

    }
}

