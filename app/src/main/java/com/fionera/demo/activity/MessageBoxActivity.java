package com.fionera.demo.activity;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fionera.demo.R;
import com.fionera.demo.adapter.MessageBoxListAdapter;
import com.fionera.demo.model.MessageBean;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageBoxActivity
        extends BaseActivity {
    public RecyclerView rvMessageList;
    public List<MessageBean> messages = new ArrayList<>();
    public SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        String thread = getIntent().getStringExtra("threadId");
        init(thread);
    }

    private void init(String thread) {
        rvMessageList = (RecyclerView) findViewById(R.id.rv_message_list);
        rvMessageList.setLayoutManager(new LinearLayoutManager(context));
        rvMessageList.setAdapter(new MessageBoxListAdapter(context, messages));

        Uri uri = Uri.parse("content://sms");
        String[] projection = new String[]{"date", "address", "person", "body", "type"}; // 查询的列
        new MessageAsyncQueryHandler(getContentResolver(),MessageBoxActivity.this).startQuery(0, null, uri, projection, "thread_id = " + thread, null, "date asc");
    }

    private static class MessageAsyncQueryHandler
            extends AsyncQueryHandler {

        private WeakReference<MessageBoxActivity> weakReference;

        MessageAsyncQueryHandler(ContentResolver cr,MessageBoxActivity messageBoxActivity) {
            super(cr);
            weakReference = new WeakReference<>(messageBoxActivity);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            MessageBoxActivity messageBoxActivity = weakReference.get();
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String date = messageBoxActivity.sdf.format(
                            new Date(cursor.getLong(cursor.getColumnIndex("date"))));
                    if (cursor.getInt(cursor.getColumnIndex("type")) == 1) {
                        MessageBean d = new MessageBean(
                                cursor.getString(cursor.getColumnIndex("address")), date,
                                cursor.getString(cursor.getColumnIndex("body")), 1);
                        messageBoxActivity.messages.add(d);
                    } else {
                        MessageBean d = new MessageBean(
                                cursor.getString(cursor.getColumnIndex("address")), date,
                                cursor.getString(cursor.getColumnIndex("body")), 2);
                        messageBoxActivity.messages.add(d);
                    }
                }
                messageBoxActivity.rvMessageList.getAdapter().notifyDataSetChanged();
            }
            super.onQueryComplete(token, cookie, cursor);
        }
    }
}
