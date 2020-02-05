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

    EditText add_ktapikey;
    EditText add_ktsecretkey;
    EditText add_awsaccesskey;
    EditText add_awssecretkey;
    EditText add_awsregion;
    EditText add_azuretenantid;
    EditText add_azureclient;
    EditText add_azurekey;
    EditText add_azuresubscription_id;

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

        KT = (Button) findViewById(R.id.KT);
        AWS = (Button) findViewById(R.id.AWS);
        Azure = (Button) findViewById(R.id.Azure);

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
        if (v == KT) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag_kt).commit();
        } else if (v == AWS) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag_aws).commit();
        } else if (v == Azure) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, frag_azure).commit();
        }
    }

    public void onFragmentChange(int index) {
        if(index == 0) {
            add_ktapikey = (EditText) findViewById(R.id.add_ktapikey);
            add_ktsecretkey = (EditText) findViewById(R.id.add_ktsecretkey);
            String get_ktapikey = add_ktapikey.getText().toString();
            String get_ktsecretkey = add_ktsecretkey.getText().toString();

            if (TextUtils.isEmpty(get_ktapikey)) {
                Toast.makeText(getApplicationContext(), "write your API_KEY", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_ktsecretkey)) {
                Toast.makeText(getApplicationContext(), "write your SECRET_KEY", Toast.LENGTH_SHORT).show();
                return;
            }

            //hashmap 만들기
            HashMap<String, String> result = new HashMap<>();
            result.put(get_ktapikey, get_ktsecretkey);

            //firebase 정의
            mDatabase = FirebaseDatabase.getInstance().getReference();
            //firebase에 저장
            firebaseUser = firebaseAuth.getCurrentUser();

            mDatabase.child(firebaseUser.getUid()).child("KT").child("Key").setValue(result);
            Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(index == 1) {
            add_awsaccesskey = (EditText) findViewById(R.id.add_awsaccesskey);
            add_awssecretkey = (EditText) findViewById(R.id.add_awssecretkey);
            add_awsregion = (EditText) findViewById(R.id.add_awsregion);
            String get_awsaccesskey = add_awsaccesskey.getText().toString();
            String get_awssecretkey = add_awssecretkey.getText().toString();
            String get_awsregion = add_awsregion.getText().toString();

            if (TextUtils.isEmpty(get_awsaccesskey)) {
                Toast.makeText(getApplicationContext(), "write your ACCESS_KEY", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_awssecretkey)) {
                Toast.makeText(getApplicationContext(), "write your SECRET_KEY", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_awsregion)) {
                Toast.makeText(getApplicationContext(), "write your REGION", Toast.LENGTH_SHORT).show();
                return;
            }

            //hashmap 만들기
            HashMap<String, String> result = new HashMap<>();
            result.put(get_awsaccesskey, get_awssecretkey+", "+get_awsregion);

            //firebase 정의
            mDatabase = FirebaseDatabase.getInstance().getReference();
            //firebase에 저장
            firebaseUser = firebaseAuth.getCurrentUser();

            mDatabase.child(firebaseUser.getUid()).child("AWS").child("Key").setValue(result);
            Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if(index == 2) {
            add_azuretenantid = (EditText) findViewById(R.id.add_azuretenantid);
            add_azureclient = (EditText) findViewById(R.id.add_azureclient);
            add_azurekey = (EditText) findViewById(R.id.add_azurekey);
            add_azuresubscription_id = (EditText) findViewById(R.id.add_azuresubscription_id);
            String get_azuretenantid = add_azuretenantid.getText().toString();
            String get_azureclient =  add_azureclient.getText().toString();
            String get_azurekey = add_azurekey.getText().toString();
            String get_azuresubscription_id = add_azuresubscription_id.getText().toString();

            if (TextUtils.isEmpty(get_azuretenantid)) {
                Toast.makeText(getApplicationContext(), "write your TENANT_ID", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_azureclient)) {
                Toast.makeText(getApplicationContext(), "write your CLIENT", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_azurekey)) {
                Toast.makeText(getApplicationContext(), "write your KEY", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(get_azuresubscription_id)) {
                Toast.makeText(getApplicationContext(), "write your SUBSCRIPTION_ID", Toast.LENGTH_SHORT).show();
                return;
            }

            //hashmap 만들기
            HashMap<String, String> result = new HashMap<>();
            result.put(get_azuretenantid, get_azureclient+", "+get_azurekey+", "+get_azuresubscription_id);

            //firebase 정의
            mDatabase = FirebaseDatabase.getInstance().getReference();
            //firebase에 저장
            firebaseUser = firebaseAuth.getCurrentUser();

            mDatabase.child(firebaseUser.getUid()).child("Azure").child("Key").setValue(result);
            Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}

