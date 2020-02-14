package com.example.example01;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LBAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<LBData> lblist;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public LBAdapter(Context mContext, List lblist) {
        this.mContext = mContext;
        this.lblist = lblist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        LayoutInflater inf = LayoutInflater.from(mContext);
        view = inf.inflate(R.layout.list_item2, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = ((MessageViewHolder) holder);

        messageViewHolder.onBind(lblist.get(position), position);

        //레이아웃
//        messageViewHolder.name.setText(vmlist.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return lblist.size();
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView state;
        public TextView zonename;
        public TextView serviceip;
        public TextView zonenameval;
        public TextView serviceipval;
        private ConstraintLayout listitem;
        private LBData data;
        private int position;


        public MessageViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            state = view.findViewById(R.id.state);
            zonename = view.findViewById(R.id.zonename);
            serviceip = view.findViewById(R.id.serviceip);
            zonenameval = view.findViewById(R.id.zonenameval);
            serviceipval = view.findViewById(R.id.serviceipval);
            listitem = view.findViewById(R.id.listitem);
        }

        void onBind(LBData data, int position) {
            this.data = data;
            this.position = position;

            name.setText(data.getName());
            state.setText(data.getState());
            zonenameval.setText(lblist.get(position).getZonename());
            serviceipval.setText(lblist.get(position).getServiceip());

            changeVisibility(selectedItems.get(position));

            listitem.setOnClickListener(this);
        }

        //list click해서 접고 펼치기
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.listitem:
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

                    zonename.requestLayout();
                    zonenameval.requestLayout();
                    serviceip.requestLayout();
                    serviceipval.requestLayout();

                    // imageView가 실제로 사라지게하는 부분
                    zonename.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    serviceip.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    zonenameval.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    serviceipval.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }
    }
}
