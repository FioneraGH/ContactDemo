package com.fionera.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fionera.demo.R;
import com.fionera.demo.model.CallLogBean;

import java.util.List;

public class CallLogAdapter
		extends RecyclerView.Adapter<CallLogAdapter.CallLogHolder> {

	private Context context;
	private List<CallLogBean> callLogs;
	private LayoutInflater inflater;

	public CallLogAdapter(Context context, List<CallLogBean> callLogs) {
		this.context = context;
		this.callLogs = callLogs;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public CallLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new CallLogHolder(inflater.inflate(R.layout.rv_contact_record_list_item,parent,false));
	}

	@Override
	public void onBindViewHolder(CallLogHolder holder, int position) {

		final CallLogBean callLog = callLogs.get(position);
		switch (callLog.getType()) {
			case 1:
				holder.call_type
						.setBackgroundResource(R.drawable.ic_call_log_incomming_normal);
				break;
			case 2:
				holder.call_type
						.setBackgroundResource(R.drawable.ic_call_log_outgoing_nomal);
				break;
			case 3:
				holder.call_type
						.setBackgroundResource(R.drawable.ic_call_log_missed_normal);
				break;
		}
		holder.name.setText(callLog.getName());
		holder.number.setText(callLog.getNumber());
		holder.time.setText(callLog.getDate());

		holder.call_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("tel:" + callLog.getNumber());
				Intent intent = new Intent(Intent.ACTION_CALL, uri);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public int getItemCount() {
		return callLogs.size();
	}

	class CallLogHolder extends RecyclerView.ViewHolder{
		ImageView call_type;
		TextView name;
		TextView number;
		TextView time;
		TextView call_btn;

		CallLogHolder(View itemView) {
			super(itemView);
			call_type = (ImageView) itemView
					.findViewById(R.id.call_type);
			name = (TextView) itemView.findViewById(R.id.name);
			number = (TextView) itemView.findViewById(R.id.number);
			time = (TextView) itemView.findViewById(R.id.time);
			call_btn = (TextView) itemView
					.findViewById(R.id.call_btn);
		}
	}
}
