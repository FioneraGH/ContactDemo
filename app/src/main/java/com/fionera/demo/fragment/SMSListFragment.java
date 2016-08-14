package com.fionera.demo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fionera.demo.R;
import com.fionera.demo.activity.MessageBoxActivity;
import com.fionera.demo.adapter.SMSAdapter;
import com.fionera.demo.model.SMSBean;
import com.fionera.demo.util.TrackSMS;

import java.util.ArrayList;
import java.util.List;

public class SMSListFragment
        extends BaseFragment {

    private List<SMSBean> list = new ArrayList<>();

    public static SMSListFragment getInstance() {
        return new SMSListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sms_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        RecyclerView rvSmsList = (RecyclerView) view.findViewById(R.id.rv_sms_list);
        rvSmsList.setLayoutManager(new LinearLayoutManager(context));
        SMSAdapter smsAdapter = new SMSAdapter(context, list);
        rvSmsList.setAdapter(smsAdapter);
        TrackSMS trackSMS = new TrackSMS(context);
        list.addAll(trackSMS.getThreadsNum(trackSMS.getThreads(0)));
        rvSmsList.getAdapter().notifyDataSetChanged();
        smsAdapter.setOnItemClickListener(new SMSAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                SMSBean smsBean = list.get(pos);
                context.startActivity(new Intent(context, MessageBoxActivity.class)
                        .putExtra("phoneNumber", smsBean.getAddress())
                        .putExtra("threadId", smsBean.getThread_id()));
            }
        });
    }
}
