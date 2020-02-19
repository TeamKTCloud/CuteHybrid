package com.example.example01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class Dash_Navi_Activity extends AppCompatActivity {

    private static final String TAG = "Dash_Navi_Activity";
    FragmentManager fragmentManager = getSupportFragmentManager();

    BottomNavigationView bottomNavigationView;
    FragmentTransaction fragmentTransaction;
    Swipe_Fragment swipeFragment = new Swipe_Fragment();
    ServiceFragment serviceFragment = new ServiceFragment();
    SettingFragment settingFragment = new SettingFragment();

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private TextView textViewUserUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash__navi_);
        bottomNavigationView = findViewById(R.id.nav_view);


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

        //제일 처음 띄워줄 뷰 세티
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,swipeFragment).commitAllowingStateLoss();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        DatabaseReference fcmToken = database.getReference(firebaseUser.getUid()).child("Token").child("token");
                        fcmToken.setValue(token);
                    }
                });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.frameLayout,swipeFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_service:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.frameLayout,serviceFragment).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_setting:
                        getSupportFragmentManager().beginTransaction() .replace(R.id.frameLayout,settingFragment).commitAllowingStateLoss();
                        return true;
                }
                return false;
            }
        });
    }

    public void onFragmentChange(int index) {
        if (index == 0) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(index == 1) {
            startActivity(new Intent(this, AddActivity.class));
        }
    }
}