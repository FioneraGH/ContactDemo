package com.fionera.demo.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fionera on 16-8-9.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        context = this;
    }
}
