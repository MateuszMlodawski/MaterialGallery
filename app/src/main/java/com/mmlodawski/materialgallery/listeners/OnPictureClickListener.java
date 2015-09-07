/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.listeners;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.activity.PictureActivity;

public class OnPictureClickListener implements View.OnClickListener {

    public static final int NO_COLOR = -1;
    private Activity callingActivity;
    private Class<?> startingActivityClass;
    private View sharedView;
    private String url;
    private int color = NO_COLOR;

    public OnPictureClickListener(Activity callingActivity, Class<?> startingActivityClass, String url) {
        this(callingActivity, startingActivityClass, url, null);
    }

    public OnPictureClickListener(Activity callingActivity, Class<?> startingActivityClass, String url, View sharedView) {
        this(callingActivity, startingActivityClass, url, sharedView, NO_COLOR);
    }

    public OnPictureClickListener(Activity callingActivity, Class<?> startingActivityClass, String url, View sharedView, int color) {
        this.callingActivity = callingActivity;
        this.startingActivityClass = startingActivityClass;
        this.sharedView = sharedView;
        this.url = url;
        this.color = color;
    }

    @Override
    public void onClick(View v) {
        if (callingActivity == null || startingActivityClass == null) return;

        Intent intent = new Intent(callingActivity, startingActivityClass);
        intent.putExtra(PictureActivity.EXTRA_URL, url);
        if (color != NO_COLOR) {
            intent.putExtra(PictureActivity.EXTRA_COLOR, color);
        }

        // Try to make Activity Transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && sharedView != null) {
            String transitionName = callingActivity.getString(R.string.detail_picture_shared_element);
            ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(callingActivity, sharedView, transitionName);
            callingActivity.startActivity(intent, transitionActivityOptions.toBundle());
        } else {
            callingActivity.startActivity(intent);
        }
    }
}
