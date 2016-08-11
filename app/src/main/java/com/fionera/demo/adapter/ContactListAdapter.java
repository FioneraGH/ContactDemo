package com.fionera.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.fionera.demo.R;
import com.fionera.demo.model.ContactBean;

import java.util.List;
import java.util.regex.Pattern;

public class ContactListAdapter
        extends RecyclerView.Adapter<ContactListAdapter.ContactHolder> {
    private List<ContactBean> list;
    private Context context;

    public ContactListAdapter(Context context, List<ContactBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(LayoutInflater.from(context).inflate(R.layout.rv_contact_list_item,parent, false));
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        ContactBean contact = list.get(position);
        String name = contact.getDesplayName();
        String number = contact.getPhoneNum();
        holder.name.setText(name);
        holder.number.setText(number);
        holder.quickContactBadge.setImageResource(R.mipmap.ic_launcher);
        //        holder.quickContactBadge.assignContactUri(
        //                Contacts.getLookupUri(contact.getContactId(), contact.getLookUpKey()));
        //        if (0 == contact.getPhotoId()) {
        //            holder.quickContactBadge.setImageResource(R.drawable.touxiang);
        //        } else {
        //            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,
        //                    contact.getContactId());
        //            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(
        //                    context.getContentResolver(), uri);
        //            Bitmap contactPhoto = BitmapFactory.decodeStream(input);
        //            holder.quickContactBadge.setImageBitmap(contactPhoto);
        //        }
        String currentStr = getAlpha(contact.getSortKey());
        String previewStr = (position - 1) >= 0 ? getAlpha(
                list.get(position - 1).getSortKey()) : "";

        if (!TextUtils.equals(currentStr,previewStr)) {
            holder.alpha.setVisibility(View.VISIBLE);
            holder.alpha.setText(currentStr);
        } else {
            holder.alpha.setVisibility(View.GONE);
            holder.alpha.setText(previewStr);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder{
        TextView alpha;
        QuickContactBadge quickContactBadge;
        TextView name;
        TextView number;

        ContactHolder(View itemView) {
            super(itemView);

            alpha = (TextView) itemView.findViewById(R.id.alpha);
            quickContactBadge = (QuickContactBadge) itemView.findViewById(R.id.qcb);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
        }
    }

    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }
}
