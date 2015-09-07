/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.presenter;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mmlodawski.materialgallery.view.PhotoDetailView;

public class PhotoDetailPresenter implements PhotoDetailPresenterInterface {
    private PhotoDetailView photoDetailView;

    @Override
    public void setView(PhotoDetailView photoDetailView) {
        this.photoDetailView = photoDetailView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    public void fetchImageUrl(final Activity activity, String url, ImageView imageView, final boolean shouldStartPostponedEnterTransition) {
        Glide.with(activity)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        // Start Activity Transition when resource is ready.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (shouldStartPostponedEnterTransition) {
                                activity.startPostponedEnterTransition();
                            }
                        }
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
