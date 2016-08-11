package com.fionera.demo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fionera.demo.fragment.ContactListFragment;
import com.fionera.demo.fragment.ContactRecordFragment;
import com.fionera.demo.fragment.SMSListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fionera on 16-8-9.
 */

public class MainPageAdapter
        extends FragmentPagerAdapter {

    private static String[] titles = {"拨号", "联系人", "短信"};

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return (0 == position) ? ContactRecordFragment
                .getInstance() : (1 == position) ? ContactListFragment
                .getInstance() : SMSListFragment.getInstance();
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position % titles.length];
    }
}
