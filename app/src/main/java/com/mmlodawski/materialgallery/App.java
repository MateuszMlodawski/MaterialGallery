/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueueManager;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        RequestQueueManager.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
