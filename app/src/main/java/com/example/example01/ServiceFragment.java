package com.example.example01;

import android.os.Bundle;
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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;

    GraphView graphView;
    LineGraphSeries series;
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
//        graphView = (GraphView) rootview.findViewById(R.id.chart);
//        series = new LineGraphSeries();
//        graphView.addSeries(series);

        mDatabase = firebaseDatabase.getReference(firebaseUser.getUid()).child("KT").child("Monitoring").child("CPUUtilization").child("JSM");


        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Entry> dataVals = new ArrayList<Entry>();
//                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
//                int index = 0;

                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Log.d("ServiceFragment", "graph : " + snapshot.getValue());
                        PointValueData data = snapshot.getValue(PointValueData.class);
//                    dp[index] = new DataPoint( data.getTime(), data.getAverage());
//                    index++;
                        dataVals.add(new Entry(data.getTime(), data.getAverage()));
                    }
//                series.resetData(dp);
                    showChart(dataVals);
                    setadapter(dataVals);
                }
                else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            private void showChart(ArrayList<Entry> dataVals) {
                lineDataSet.setValues(dataVals);
                lineDataSet.setLabel("DataSet 1");
                iLineDataSets.clear();
                iLineDataSets.add(lineDataSet);
                lineData = new LineData(iLineDataSets);
                lineChart.clear();
                lineChart.setData(lineData);
                lineChart.invalidate();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        mDatabase.addValueEventListener(postListener);


        return rootview;
    }
    public void setadapter( ArrayList<Entry> dataVals) {
        GraphAdapter graphadapter = new GraphAdapter(getContext(), dataVals);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(graphadapter);
    }
}
