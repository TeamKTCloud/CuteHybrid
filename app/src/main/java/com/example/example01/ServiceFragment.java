package com.example.example01;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServiceFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase , mDatabase_KT, mDatabase_AWS, mDatabase_Azure;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private ArrayList<Metric> list;
    private ArrayList<Entry> dataVals;

    LineChart lineChart;
    LineDataSet lineDataSet = new LineDataSet(null, null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    private ArrayList<String> metrics = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup) inflater.inflate(R.layout.fragment_service, container, false);

        progressDialog = new ProgressDialog(getContext());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        lineChart = (LineChart) rootview.findViewById(R.id.chart);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recyclerView2);

        list = new ArrayList<>();
        dataVals = new ArrayList<Entry>();

//        mDatabase = firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Monitoring");
//        mDatabase_KT = firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Monitoring").child("CPUUtilization");
//        mDatabase_AWS = firebaseDatabase.getReference(firebaseUser.getUid()).child("AWS").child("Monitoring").child("CPUUtilization");
//        mDatabase_Azure = firebaseDatabase.getReference(firebaseUser.getUid()).child("Azure").child("Monitoring").child("CPUUtilization");
        mDatabase_Provider = firebaseDatabase.getReference(firebaseUser.getUid()).child(provider).child("Monitoring").child("CPUUtilization");

       final ArrayList<String> vmlists = new ArrayList<>();

        //모니터링 recyclerview.......
        mDatabase_Provider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String vm_name = snapshot.getKey();
                    mDatabase_Provider.child(vm_name).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            HashMap<String, Float> time_metric = new LinkedHashMap<>(); // 데이터 순차 삽입을 위해 LinkedHashMap
                            for (DataSnapshot monitoring_data : dataSnapshot2.getChildren()) {
                                PointValueData pvd = monitoring_data.getValue(PointValueData.class);
                                time_metric.put(pvd.getTime(), Float.valueOf(pvd.getAverage()));
                            }
                            Metric m = new Metric(vm_name, time_metric);
                            list.add(m);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                setadapter(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return rootview;
    }
    public void setadapter(ArrayList<Metric> list) {
        GraphAdapter graphadapter = new GraphAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(graphadapter);
    }
}