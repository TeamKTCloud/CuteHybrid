package com.example.example01;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase , mDatabase_KT, mDatabase_AWS;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;

    private List<PointValueData> list;
    private ArrayList<Entry> dataVals;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_service, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        lineChart = (LineChart) rootview.findViewById(R.id.chart);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerView2);

        list = new ArrayList<>();
        dataVals = new ArrayList<Entry>();

        mDatabase = firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Monitoring");
        mDatabase_KT = firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Monitoring").child("CPUUtilization");
        mDatabase_AWS = firebaseDatabase.getReference(firebaseUser.getUid()).child("AWS").child("Monitoring").child("CPUUtilization");


        //모니터링 recyclerview.......
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Entry> dataVals = new ArrayList<Entry>();
//                List<PointValueData> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PointValueData data = snapshot.getValue(PointValueData.class);
                    Log.d("ServiceFragment", "graph : " + data);
                    list.add(data);
//                    float time = Float.parseFloat(data.getTime());
//                    float average = Float.parseFloat(data.getAverage());
//                    dataVals.add(new Entry(time , average));
                }
                setadapter(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        mDatabase_AWS.child("aws_test01").addValueEventListener(postListener);
        mDatabase_AWS.child("aws_test02").addValueEventListener(postListener);
        mDatabase_KT.child("JSM").addValueEventListener(postListener);

        return rootview;
    }
    public void setadapter(List<PointValueData> list) {
        GraphAdapter graphadapter = new GraphAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(graphadapter);
    }
}