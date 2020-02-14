package com.example.example01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Swipe_Second_Fragment extends Fragment {

    //firebase auth object
    //private static final String TAG = "VMActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase_KT, mDatabase_AWS;
    private FirebaseDatabase firebaseDatabase;

    private RecyclerView recyclerView;
    private List<LBData> lblist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dash, container, false);

        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerView);

        lblist = new ArrayList<>();


        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

//      유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
//        if (firebaseAuth.getCurrentUser() == null) {
//            finish();
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }

        //User profile 가져오기
        firebaseUser = firebaseAuth.getCurrentUser();



        //firebase 정의
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase_KT =  firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Resources").child("LB");
//        mDatabase_AWS = firebaseDatabase.getReference(firebaseUser.getUid()).child("AWS").child("Resources").child("VM");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    LBData lb = snapshot.getValue(LBData.class);
                    lblist.add(lb);
                }
                setadapter(lblist);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

//        mDatabase_AWS.addValueEventListener(postListener);
        mDatabase_KT.addValueEventListener(postListener);



        return rootview;
    }
    public void setadapter(List<LBData> lblist) {
        LBAdapter lbadapter = new LBAdapter(getContext(), lblist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(lbadapter);
    }
}
