package com.example.example01;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VMAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<VMData> vmlist;
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public VMAdapter(Context mContext, List vmlist) {
        this.mContext = mContext;
        this.vmlist = vmlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        LayoutInflater inf = LayoutInflater.from(mContext);
        view = inf.inflate(R.layout.list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageViewHolder messageViewHolder = ((MessageViewHolder)holder);

        //레이아웃
        messageViewHolder.name.setText(vmlist.get(position).getName());
        messageViewHolder.state.setText(vmlist.get(position).getState());
        messageViewHolder.created.setText(vmlist.get(position).getCreated());
        messageViewHolder.zonename.setText(vmlist.get(position).getZonename());
        messageViewHolder.templatename.setText(vmlist.get(position).getTemplatename());


    }

    @Override
    public int getItemCount() {
        return vmlist.size();
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView state;
        public TextView created;
        public TextView zonename;
        public TextView templatename;


        public MessageViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            state = view.findViewById(R.id.state);
            created = view.findViewById(R.id.createdval);
            zonename = view.findViewById(R.id.zonenameval);
            templatename = view.findViewById(R.id.templatenameval);
        }
    }
}

