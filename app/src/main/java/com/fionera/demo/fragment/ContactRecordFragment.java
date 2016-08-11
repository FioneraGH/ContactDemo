package com.fionera.demo.fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fionera.demo.R;
import com.fionera.demo.adapter.CallLogAdapter;
import com.fionera.demo.model.CallLogBean;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ContactRecordFragment
        extends BaseFragment {

    public RecyclerView rvCallLog;
    public List<CallLogBean> callLogs = new ArrayList<>();

    public static ContactRecordFragment getInstance() {
        return new ContactRecordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View rootView) {
        rvCallLog = (RecyclerView) rootView.findViewById(R.id.rv_call_log);
        rvCallLog.setLayoutManager(new LinearLayoutManager(context));
        rvCallLog.setAdapter(new CallLogAdapter(context, callLogs));

        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = {CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.TYPE,
                CallLog.Calls.CACHED_NAME, CallLog.Calls._ID,};
        new WeakAsyncQueryHandler(this, context.getContentResolver()).startQuery(0, null, uri,
                projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    }

    private static class WeakAsyncQueryHandler
            extends AsyncQueryHandler {

        WeakReference<ContactRecordFragment> weakReference;

        WeakAsyncQueryHandler(ContactRecordFragment fragment, ContentResolver cr) {
            super(cr);
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            ContactRecordFragment fragment = weakReference.get();
            if (cursor != null && cursor.getCount() > 0) {
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd\nhh:mm", Locale.CHINA);
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    Date date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String cachedName = cursor.getString(
                            cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));

                    CallLogBean callLogBean = new CallLogBean();
                    callLogBean.setId(id);
                    callLogBean.setNumber(number);
                    callLogBean.setName(cachedName);
                    if (TextUtils.isEmpty(cachedName)) {
                        callLogBean.setName(number);
                    }
                    callLogBean.setType(type);
                    callLogBean.setDate(sfd.format(date));

                    if (fragment != null) {
                        fragment.callLogs.add(callLogBean);
                    }
                }
                if (fragment != null) {
                    fragment.rvCallLog.getAdapter().notifyDataSetChanged();
                }
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }
}
