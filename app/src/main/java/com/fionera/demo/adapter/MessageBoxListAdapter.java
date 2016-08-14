package com.fionera.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fionera.demo.R;
import com.fionera.demo.model.MessageBean;

import java.util.List;

public class MessageBoxListAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_OUT_INCOMING = 1000;
    private final int TYPE_OUT_OUTGOING = 1001;
    private List<MessageBean> list;
    private Context context;

    public MessageBoxListAdapter(Context context, List<MessageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return (list.get(position).getType() == 1) ? TYPE_OUT_INCOMING : TYPE_OUT_OUTGOING;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_OUT_INCOMING) {
            holder = new MessageHolder(LayoutInflater.from(context)
                    .inflate(R.layout.rv_message_incoming_item, parent, false));
        } else {
            holder = new MessageOutHolder(LayoutInflater.from(context)
                    .inflate(R.layout.rv_message_outgoing_item, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_OUT_INCOMING){
            MessageHolder theHolder = (MessageHolder)holder;
            theHolder.tvDetail.setText(list.get(position).getText());
            theHolder.tvTime.setText(list.get(position).getDate());
        }else{
            MessageOutHolder theHolder = (MessageOutHolder)holder;
            theHolder.tvDetail.setText(list.get(position).getText());
            theHolder.tvTime.setText(list.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private class MessageHolder
            extends RecyclerView.ViewHolder {

        TextView tvDetail;
        TextView tvTime;

        MessageHolder(View itemView) {
            super(itemView);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_message_detail_row_text);
            tvTime = (TextView) itemView.findViewById(R.id.tv_message_detail_row_date);
        }
    }

    private class MessageOutHolder
            extends RecyclerView.ViewHolder {

        TextView tvDetail;
        TextView tvTime;

        MessageOutHolder(View itemView) {
            super(itemView);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_message_detail_row_text);
            tvTime = (TextView) itemView.findViewById(R.id.tv_message_detail_row_date);
        }
    }
}
