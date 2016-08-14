package com.fionera.demo.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.SearchEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;

import com.fionera.demo.BaseApplication;
import com.fionera.demo.R;
import com.fionera.demo.adapter.MainPageAdapter;
import com.fionera.demo.util.DisplayUtil;
import com.fionera.demo.util.MessageEvent;
import com.fionera.demo.view.BottomSheetDialogView;

import org.greenrobot.eventbus.EventBus;

public class MainActivity
        extends BaseActivity
        implements View.OnClickListener {

    private FloatingActionButton fabMainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TabLayout tlMainPage = (TabLayout) findViewById(R.id.tl_main_page);
        final ViewPager vpMainPage = (ViewPager) findViewById(R.id.vp_main_page);
        fabMainPage = (FloatingActionButton) findViewById(R.id.fab_main_page);

        tlMainPage.setTranslationY(-DisplayUtil.dp2px(48));
        tlMainPage.animate().translationY(0).setDuration(500).start();
        fabMainPage.setTranslationY(DisplayUtil.dp2px(100));
        fabMainPage.animate().translationY(0).setDuration(1000).setInterpolator(
                new OvershootInterpolator()).start();

        vpMainPage.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        vpMainPage.setOffscreenPageLimit(2);
        vpMainPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                if (position >= 1) {
                    return;
                } else {
                    position++;
                }
                fabMainPage.setTranslationX(
                        position * positionOffset * BaseApplication.screenWidth / 3);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fabMainPage.setOnClickListener(MainActivity.this);
                    fabMainPage.setImageResource(R.drawable.ic_call_log_call_active);
                } else if (position == 1) {
                    fabMainPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final EditText etSearch = new EditText(context);
                            new AlertDialog.Builder(context).setView(etSearch).setTitle("请输入搜索内容").setPositiveButton(
                                    "搜索", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface,
                                                            int i) {
                                            dialogInterface.dismiss();
                                            EventBus.getDefault().post(new MessageEvent.SearchEvent(
                                                    etSearch.getText().toString()));
                                        }
                                    }).setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialogInterface,
                                                            int i) {
                                            dialogInterface.dismiss();
                                        }
                                    }).show();

                        }
                    });
                    fabMainPage.setImageResource(R.drawable.search);
                } else {
                    fabMainPage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Snackbar.make(vpMainPage, "这啥都没有", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    fabMainPage.setImageResource(R.drawable.ic_call_log_call_active);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tlMainPage.setupWithViewPager(vpMainPage);

        fabMainPage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        view.animate().scaleX(0).scaleY(0).setDuration(700).start();
        BottomSheetDialogView.show(context, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                fabMainPage.animate().scaleX(1).scaleY(1).setDuration(700).start();
            }
        });
    }
}
