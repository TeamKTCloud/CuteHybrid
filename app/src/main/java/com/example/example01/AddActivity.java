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

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    KT_key_FragmentActivity frag_KT;
    AWS_key_FragmentActivity frag_AWS;

    EditText add_ktapikey;
    EditText add_ktsecretkey;
    EditText add_awsaccesskey;
    EditText add_awssecretkey;
    EditText add_awsregion;

    Button kt_add_apply;
    Button aws_add_apply;
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

        //fragment 정의
        fragmentManager = getSupportFragmentManager();
        frag_KT = new KT_key_FragmentActivity();
        frag_AWS = new AWS_key_FragmentActivity();

        //아이디 정의
        KT = (Button)findViewById(R.id.KT);
        AWS = (Button)findViewById(R.id.AWS);
        Azure = (Button)findViewById(R.id.Azure);
        kt_add_apply = (Button) findViewById(R.id.kt_add_apply);
        aws_add_apply = (Button) findViewById(R.id.aws_add_apply);
        add_ktapikey = (EditText) findViewById(R.id.add_ktapikey);
        add_ktsecretkey = (EditText) findViewById(R.id.add_ktsecretkey);
        add_awsaccesskey = (EditText) findViewById(R.id.add_awsaccesskey);
        add_awssecretkey = (EditText) findViewById(R.id.add_awssecretkey);
        add_awsregion = (EditText) findViewById(R.id.add_awsregion);

        firebaseAuth = FirebaseAuth.getInstance();

        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //유저가 있다면, null이 아니면 계속 진행
        kt_add_apply.setOnClickListener(this);
        aws_add_apply.setOnClickListener(this);
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
        transaction = fragmentManager.beginTransaction();
//        if (v == kt_add_apply) {
//            //edittext에 저장된 텍스트 Strig에 저장
//            String get_ktapikey = add_ktapikey.getText().toString();
//            String get_ktsecretkey = add_ktsecretkey.getText().toString();
//
//            if (TextUtils.isEmpty(get_ktapikey)) {
//                Toast.makeText(getApplicationContext(), "write your API_KEY", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(get_ktsecretkey)) {
//                Toast.makeText(getApplicationContext(), "write your SECRET_KEY", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            //hashmap 만들기
//            HashMap<String, String> result = new HashMap<>();
//            result.put("API_KEY", get_ktapikey);
//            result.put("SECRET_KEY", get_ktsecretkey);
//
//            //firebase 정의
//            mDatabase = FirebaseDatabase.getInstance().getReference();
//            //firebase에 저장
//            firebaseUser = firebaseAuth.getCurrentUser();
//
//            mDatabase.child(firebaseUser.getUid()).child("KT").child("Key").setValue(result);
//            Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        if (v == aws_add_apply) {
//            //edittext에 저장된 텍스트 Strig에 저장
//            String get_awsaccesskey = add_awsaccesskey.getText().toString();
//            String get_awssecretkey = add_awssecretkey.getText().toString();
//            String get_awsregion = add_awsregion.getText().toString();
//
//            if (TextUtils.isEmpty(get_awsaccesskey)) {
//                Toast.makeText(getApplicationContext(), "write your aws_access_key_id", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(get_awssecretkey)) {
//                Toast.makeText(getApplicationContext(), "write your aws_secret_access_key", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (TextUtils.isEmpty(get_awsregion)) {
//                Toast.makeText(getApplicationContext(), "write your region", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            //hashmap 만들기
//            HashMap<String, String> result = new HashMap<>();
//            result.put("access_key_id", get_awsaccesskey);
//            result.put("secret_access_key", get_awssecretkey);
//            result.put("region", get_awsregion);
//
//            //firebase 정의
//            mDatabase = FirebaseDatabase.getInstance().getReference();
//            //firebase에 저장
//            firebaseUser = firebaseAuth.getCurrentUser();
//
//            mDatabase.child(firebaseUser.getUid()).child("AWS").child("Key").setValue(result);
//            Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
//            finish();
//        }
        if(v == KT) {
            transaction.replace(R.id.add, frag_KT).commitAllowingStateLoss();
        }
        if(v == AWS) {
            transaction.replace(R.id.add, frag_AWS).commitAllowingStateLoss();
        }
    }
}
