package com.example.example01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        //아이디 정의
        Button add_apply = (Button) findViewById(R.id.add_apply);
        final EditText add_apikey = (EditText) findViewById(R.id.add_apikey);
        final EditText add_secretkey = (EditText) findViewById(R.id.add_secretkey);

        add_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //edittext에 저장된 텍스트 Strig에 저장
                String get_apikey = add_apikey.getText().toString();
                String get_secretkey = add_secretkey.getText().toString();

                if(TextUtils.isEmpty(get_apikey)) {
                    Toast.makeText(getApplicationContext(), "write your API_KEY", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(get_secretkey)) {
                    Toast.makeText(getApplicationContext(), "write your SECRET_KEY", Toast.LENGTH_SHORT).show();
                    return;
                }

                //hashmap 만들기
                HashMap<String, String> result = new HashMap<>();
                result.put("API_KEY", get_apikey );
                result.put("SECRET_KEY", get_secretkey);

                //firebase 정의
                mDatabase = FirebaseDatabase.getInstance().getReference();
                //firebase에 저장
                mDatabase.child("User").child("KT").child("Key").setValue(result);

                Toast.makeText(getApplicationContext(), "add success.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
