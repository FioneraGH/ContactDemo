package com.fionera.demo.fragment;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.fionera.demo.R;
import com.fionera.demo.adapter.ContactListAdapter;
import com.fionera.demo.model.ContactBean;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ContactListFragment
		extends BaseFragment {

	public RecyclerView rvContactList;
    public List<ContactBean> list = new ArrayList<>();

    public static ContactListFragment getInstance() {
		return new ContactListFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_contact_list, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		init(view);
	}

	private void init(View view) {
        rvContactList = (RecyclerView) view.findViewById(R.id.rv_contact_list);
        rvContactList.setLayoutManager(new LinearLayoutManager(context));
        rvContactList.setAdapter(new ContactListAdapter(context, list));

		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String[] projection = { ContactsContract.CommonDataKinds.Phone._ID,
				ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
				ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key",
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
				ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
				ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY };
        new WeakAsyncQueryHandler(this,context.getContentResolver()).startQuery(0, null, uri, projection, null, null,
				"sort_key COLLATE LOCALIZED asc");
	}

	private static class WeakAsyncQueryHandler extends AsyncQueryHandler {

        private WeakReference<ContactListFragment> weakReference;

		WeakAsyncQueryHandler(ContactListFragment fragment, ContentResolver cr) {
			super(cr);
            weakReference = new WeakReference<>(fragment);
		}

		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            ContactListFragment fragment = weakReference.get();
			if (cursor != null && cursor.getCount() > 0) {
                SparseArray<ContactBean> contactIdMap = new SparseArray<>();
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);
					int contactId = cursor.getInt(4);
					Long photoId = cursor.getLong(5);
					String lookUpKey = cursor.getString(6);

					if (contactIdMap.indexOfKey(contactId) < 0) {
						ContactBean contact = new ContactBean();
						contact.setDesplayName(name);
						contact.setPhoneNum(number);
						contact.setSortKey(sortKey);
						contact.setPhotoId(photoId);
						contact.setLookUpKey(lookUpKey);
                        if (fragment != null) {
                            fragment.list.add(contact);
                        }
						contactIdMap.put(contactId, contact);
					}
				}
                if (fragment != null) {
                    fragment.rvContactList.getAdapter().notifyDataSetChanged();
                }
			}
			super.onQueryComplete(token, cookie, cursor);
		}
	}
}
