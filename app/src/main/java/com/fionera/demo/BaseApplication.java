package com.fionera.demo;

import android.app.Application;
import android.util.DisplayMetrics;

public class BaseApplication
        extends Application {

    private static BaseApplication application;
    public static int screenWidth;
    public static int screenHeight;
    public static float screenDensity;
    public static float scaledDensity;

    public static BaseApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //        if (BuildConfig.DEBUG) {
        //            StrictMode.setVmPolicy(
        //                    new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        //            StrictMode.setThreadPolicy(
        //                    new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog()
        //                            .penaltyDeathOnNetwork().build());
        //        }

        application = this;
        getDisplayMetrics();
    }

    /**
     * 获取高度、宽度、密度、缩放比例
     */
    private void getDisplayMetrics() {
        DisplayMetrics metric = getApplicationContext().getResources().getDisplayMetrics();
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
        screenDensity = metric.density;
        scaledDensity = metric.scaledDensity;
    }
}
