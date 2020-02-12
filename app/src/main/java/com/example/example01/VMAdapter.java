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
        messageVieHolder.list_title.setText(vmlist.get(position).getCreated());

    }

    @Override
    public int getItemCount() {
        return vmlist.size();
    }

    private class MessageVieHolder extends RecyclerView.ViewHolder {
        public TextView list_title;


        public MessageVieHolder(View view) {
            super(view);
            list_title = view.findViewById(R.id.list_title);

        }
    }
}

