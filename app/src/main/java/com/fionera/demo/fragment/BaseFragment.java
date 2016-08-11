package com.fionera.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by fionera on 16-8-9.
 */

public class BaseFragment extends Fragment {

    protected Context context;
    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }
}
