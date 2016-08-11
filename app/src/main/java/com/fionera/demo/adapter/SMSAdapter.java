package com.fionera.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fionera.demo.R;
import com.fionera.demo.model.SMSBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SMSAdapter
        extends RecyclerView.Adapter<SMSAdapter.SMSListHolder> {
    private LayoutInflater layoutInflater;
    private List<SMSBean> smsList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int pos);
    }

    public SMSAdapter(Context context, List<SMSBean> smsList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.smsList = smsList;
    }

    @Override
    public SMSListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SMSListHolder(layoutInflater.inflate(R.layout.rv_sms_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final SMSListHolder holder, int position) {
        holder.name.setText(smsList.get(position).getAddress());
        holder.count.setText("(" + smsList.get(position).getMsg_count() + ")");
        holder.date.setText(new SimpleDateFormat("MM/dd HH:mm", Locale.CHINA)
                .format(new Date(smsList.get(position).getDate())));
        holder.content.setText(smsList.get(position).getMsg_snippet());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class SMSListHolder
            extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView count;
        public TextView date;
        public TextView content;

        SMSListHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            count = (TextView) itemView.findViewById(R.id.count);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
