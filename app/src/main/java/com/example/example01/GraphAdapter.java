package com.example.example01;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.LabelFormatter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphAdapter extends RecyclerView.Adapter {

    private ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    private LineData lineData;
    private Context mContext;
    private ArrayList<Entry> dataVals;
    private ArrayList<Metric> list;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public GraphAdapter(Context mContext, ArrayList list) {
        this.mContext = mContext;
        this.dataVals = dataVals;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        LayoutInflater inf = LayoutInflater.from(mContext);
        view = inf.inflate(R.layout.graph_list, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = ((MessageViewHolder) holder);

        messageViewHolder.onBind(list.get(position), position);



        //레이아웃
//        messageViewHolder.name.setText(vmlist.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    private class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,  View.OnCreateContextMenuListener {
    private class MessageViewHolder extends RecyclerView.ViewHolder implements  View.OnCreateContextMenuListener {
        public ImageView imageView;
        public TextView name;
        public TextView cpu;
        public LineChart chart;
        private ConstraintLayout graphitem;
        private int position;



    public MessageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            name = view.findViewById(R.id.name);
            cpu = view.findViewById(R.id.cpu);
            chart = (LineChart) view.findViewById(R.id.chart);
            graphitem = view.findViewById(R.id.graphitem);
        }


        void onBind(Metric vm_metric, int position) {
            this.position = position;
            String vm_name = vm_metric.getVmName();
            name.setText(vm_name);

            Iterator<String> keySetIterator = vm_metric.getData().keySet().iterator();

            String[] timeStrings = new String[vm_metric.getData().size()];
            Float[] cpuMetrics = new Float[vm_metric.getData().size()];

            for(int i = 0; i < vm_metric.getData().size(); i++){
                String key = keySetIterator.next();
                timeStrings[i] = key;
                cpuMetrics[i] = vm_metric.getData().get(key);
            }

            showChart(timeStrings, cpuMetrics);

            changeVisibility(selectedItems.get(position));
//            graphitem.setOnClickListener(this);
//            graphitem.setOnCreateContextMenuListener(this);
        }

        private void showChart(String[] timeStrings, Float[] cpuMetrics) {

            ArrayList<Entry> vm_cpu_data = new ArrayList<>();

            for (int i = 0; i < cpuMetrics.length; i++) {
                vm_cpu_data.add(new Entry(i, cpuMetrics[i]));
            }

            LineDataSet lineDataSet = new LineDataSet(vm_cpu_data, null);
            lineDataSet.setLabel("CPU Utilization(percent)");
            LineData chartData = new LineData();
            chartData.addDataSet(lineDataSet);
            chart.setData(chartData);
            chart.getXAxis().setValueFormatter(new LabelFormatter(timeStrings));
            chart.invalidate();
        }

        //list click해서 접고 펼치기
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.graphitem:
                    if (selectedItems.get(position)) {
                        // 펼쳐진 Item을 클릭 시
                        selectedItems.delete(position);
                    } else {
                        // 직전의 클릭됐던 Item의 클릭상태를 지움
                        selectedItems.delete(prePosition);
                        // 클릭한 Item의 position을 저장
                        selectedItems.put(position, true);
                    }
                    // 해당 포지션의 변화를 알림
                    if (prePosition != -1) notifyItemChanged(prePosition);
                    notifyItemChanged(position);
                    // 클릭된 position 저장
                    prePosition = position;
                    break;
            }
        }

        /**
         * 클릭된 Item의 상태 변경
         *
         * @param isExpanded Item을 펼칠 것인지 여부
         */
        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 150;
            float d = mContext.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(600);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    cpu.requestLayout();
                    chart.requestLayout();

                    // imageView가 실제로 사라지게하는 부분
                    cpu.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    chart.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                }
            });
            // Animation start
            va.start();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem Delete = menu.add(Menu.NONE, 1002, 1, "삭제");
            Delete.setOnMenuItemClickListener(onEditMenu);

        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1002:
                        list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(), list.size());
                        break;
                }
                return true;
            }
        };
    }

    public class LabelFormatter implements IAxisValueFormatter
    {
        private final String[] mLabels;

        public LabelFormatter(String[] labels)
        {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis)
        {
            return mLabels[(int) value];
        }
    }
}