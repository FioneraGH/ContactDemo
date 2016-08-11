package com.fionera.demo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.fionera.demo.model.SMSBean;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TrackSMS {

	private static final String CONTENT_URI_SMS = "content://sms";
	private static final String CONTENT_URI_SMS_INBOX = "content://sms/inbox";
	private static final String CONTENT_URI_SMS_SENT = "content://sms/sent";
	private static final String CONTENT_URI_SMS_CONVERSATIONS = "content://sms/conversations";

	public TrackSMS(Context mContext) {
		this.mContext = mContext;
	}

	private static String[] SMS_COLUMNS = new String[] { "_id",
			"thread_id",
			"address",
			"person",
			"date",
			"body",
			"read",
			"type",
			"service_center"
	};
	private static String[] THREAD_COLUMNS = new String[] { "thread_id",
			"msg_count", "snippet" };

	private Context mContext;

	public String getContentUris() {
		String rtn = "{";
		rtn += "\"sms\":\"" + CONTENT_URI_SMS + "\"";
		rtn += ",\"inbox\":\"" + CONTENT_URI_SMS_INBOX + "\"";
		rtn += ",\"sent\":\"" + CONTENT_URI_SMS_SENT + "\"";
		rtn += ",\"conversations\":\"" + CONTENT_URI_SMS_CONVERSATIONS + "\"";
		rtn += "}";
		return rtn;
	}

	public String get(int number) {
		return getData(null, number);
	}

	public String getUnread(int number) {
		return getData("type=1 AND read=0", number);
	}

	public String getRead(int number) {
		return getData("type=1 AND read=1", number);
	}

	public String getInbox(int number) {
		return getData("type=1", number);
	}

	public String getSent(int number) {
		return getData("type=2", number);
	}

	public String getByThread(int thread) {
		return getData("thread_id=" + thread, 0);
	}

	private String getData(String selection, int number) {
		Cursor cursor;
		ContentResolver contentResolver = mContext.getContentResolver();
		try {
			if (number > 0) {
				cursor = contentResolver.query(Uri.parse(CONTENT_URI_SMS),
						SMS_COLUMNS, selection, null, "date desc limit "
								+ number);
			} else {
				cursor = contentResolver.query(Uri.parse(CONTENT_URI_SMS),
						SMS_COLUMNS, selection, null, "date desc");
			}
			if (cursor == null || cursor.getCount() == 0)
				return "[]";
			String rtn = "";
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				if (i > 0)
					rtn += ",";
				rtn += "{";
				rtn += "\"_id\":" + cursor.getString(0);
				rtn += ",\"thread_id\":" + cursor.getString(1);
				rtn += ",\"address\":\"" + cursor.getString(2) + "\"";
				rtn += ",\"person\":\""
						+ ((cursor.getString(3) == null) ? "" : cursor
								.getString(3)) + "\"";
				rtn += ",\"date\":" + cursor.getString(4);
				rtn += ",\"body\":\"" + cursor.getString(5) + "\"";
				rtn += ",\"read\":"
						+ ((cursor.getInt(6) == 1) ? "true" : "false");
				rtn += ",\"type\":" + cursor.getString(7);
				rtn += ",\"service_center\":" + cursor.getString(8);
				rtn += "}";
			}
            cursor.close();
			return "[" + rtn + "]";
		} catch (Exception e) {
			return "[]";
		}
	}

	public List<SMSBean> getThreads(int number) {
		Cursor cursor;
		ContentResolver contentResolver = mContext.getContentResolver();
		List<SMSBean> list = new ArrayList<>();
		try {
			if (number > 0) {
				cursor = contentResolver.query(
						Uri.parse(CONTENT_URI_SMS_CONVERSATIONS),
						THREAD_COLUMNS, null, null, "thread_id desc limit "
								+ number);
			} else {
				cursor = contentResolver.query(
						Uri.parse(CONTENT_URI_SMS_CONVERSATIONS),
						THREAD_COLUMNS, null, null, "date desc");
			}
			if (cursor == null || cursor.getCount() == 0)
				return list;
			for (int i = 0; i < cursor.getCount(); i++) {
				cursor.moveToPosition(i);
				SMSBean mmt = new SMSBean(cursor.getString(0),
						cursor.getString(1), cursor.getString(2));
				list.add(mmt);
			}
            cursor.close();
			return list;
		} catch (Exception e) {
			return list;
		}
	}

	public List<SMSBean> getThreadsNum(List<SMSBean> ll) {

		Cursor cursor;
		ContentResolver contentResolver = mContext.getContentResolver();
		List<SMSBean> list = new ArrayList<>();
		for (SMSBean mmt : ll) {
			cursor = contentResolver.query(Uri.parse(CONTENT_URI_SMS),
					SMS_COLUMNS, "thread_id = " + mmt.getThread_id(), null,
					null);
			if (cursor == null || cursor.getCount() == 0)
				return list;
			cursor.moveToFirst();
			mmt.setAddress(cursor.getString(2));
			mmt.setDate(cursor.getLong(4));
			mmt.setRead(cursor.getString(6));
			list.add(mmt);
			cursor.close();
		}

		return list;
	}
}
