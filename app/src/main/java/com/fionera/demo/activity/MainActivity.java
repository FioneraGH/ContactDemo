package com.fionera.demo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.animation.OvershootInterpolator;

import com.fionera.demo.R;
import com.fionera.demo.adapter.MainPageAdapter;
import com.fionera.demo.util.DisplayUtil;

public class MainActivity extends BaseActivity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        TabLayout tlMainPage = (TabLayout) findViewById(R.id.tl_main_page);
        ViewPager vpMainPage = (ViewPager) findViewById(R.id.vp_main_page);
        FloatingActionButton fabMainPage = (FloatingActionButton) findViewById(R.id.fab_main_page);

        tlMainPage.setTranslationY(-DisplayUtil.dp2px(48));
        tlMainPage.animate().translationY(0).setDuration(500).start();
        fabMainPage.setTranslationY(DisplayUtil.dp2px(100));
        fabMainPage.animate().translationY(0).setDuration(1000).setInterpolator(new OvershootInterpolator()).start();

		vpMainPage.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
		vpMainPage.setOffscreenPageLimit(2);
		tlMainPage.setupWithViewPager(vpMainPage);
	}
}
