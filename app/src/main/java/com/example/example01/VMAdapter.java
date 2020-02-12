package com.example.example01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VMAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<VMData> vmlist;

    public VMAdapter(Context mContext, List vmlist) {
        this.mContext = mContext;
        this.vmlist = vmlist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
          View view = null;
          LayoutInflater inf = LayoutInflater.from(mContext);
          view = inf.inflate(R.layout.list_item, parent, false);
          return new MessageVieHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageVieHolder messageVieHolder = ((MessageVieHolder)holder);

        //레이아웃
        messageVieHolder.name.setText(vmlist.get(position).getName());
        messageVieHolder.state.setText(vmlist.get(position).getState());
        messageVieHolder.created.setText(vmlist.get(position).getCreated());
        messageVieHolder.zonename.setText(vmlist.get(position).getZonename());
        messageVieHolder.templatename.setText(vmlist.get(position).getTemplatename());

    }

    @Override
    public int getItemCount() {
        return vmlist.size();
    }

    private class MessageVieHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView state;
        public TextView created;
        public TextView zonename;
        public TextView templatename;


        public MessageVieHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            state = view.findViewById(R.id.state);
            created = view.findViewById(R.id.created);
            zonename = view.findViewById(R.id.zonenameval);
            templatename = view.findViewById(R.id.templatenameval);

        }
    }
}

