package com.example.example01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashFragment extends Fragment {

    //firebase auth object
    private static final String TAG = "VMActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;

    private ListView listView;
    private ArrayAdapter<Object> adapter;
    private List<Object> vmlist = new ArrayList<>();
    TextView textviewVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_dash, container, false);

        listView = (ListView)rootview.findViewById(R.id.listView);

        adapter = new ArrayAdapter<Object>(getActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<Object>());
        listView.setAdapter(adapter);

        textviewVM =  (TextView)rootview.findViewById(R.id.textviewVM);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //유저가 로그인 하지 않은 상태라면 null 상태이고 이 액티비티를 종료하고 로그인 액티비티를 연다.
//        if (firebaseAuth.getCurrentUser() == null) {
//            finish();
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }

        //User profile 가져오기
        firebaseUser = firebaseAuth.getCurrentUser();


        //firebase 정의
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase =  firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Resources").child("VM");


        //DB에서 정보 갖고 오기
        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                  Log.d("VMActivity", "ValueEventListener : " + snapshot.toString());
                    VM vm = new VM();
                    Object value = snapshot.getValue();
                    String str = String.valueOf(value);
                    vmlist.add(value);
                    adapter.add(value);
//                  String str = dataSnapshot.toString();
//                  Log.d("VMActivity", "Object to String  : " + str);
//                  String str = DataSnapshot.getValue(String.class);
//                  textviewVM.setText(str);

                }
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return rootview;
    }
       public class VM{
        private String State;
        private String Created;
        private String CpuSpeed;

        public String getState() {
            return State;
        }

        public String getCreated() {
            return Created;
        }

        public String getCpuSpeed() {
            return CpuSpeed;
        }

        public void setState(String State) {
            this.State = State;
        }

        public void setCreated(String Created) {
            this.Created = Created;
        }

        public void setCpuSpeed(String CpuSpeed) {
            this.CpuSpeed = CpuSpeed;
        }

    }

}

