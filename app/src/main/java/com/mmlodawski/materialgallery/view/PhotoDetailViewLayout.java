/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.presenter.PhotoDetailPresenter;

public class PhotoDetailViewLayout extends FrameLayout implements PhotoDetailView {

    private ImageView image;
    private PhotoDetailPresenter photoDetailPresenter;

    public PhotoDetailViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        photoDetailPresenter = new PhotoDetailPresenter();
        inflate(getContext(), R.layout.layout_photo_detail, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.image = (ImageView) findViewById(R.id.detail_image);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        photoDetailPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        photoDetailPresenter.onCreate(savedInstanceState);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setPictureUrl(String imageUrl) {
        photoDetailPresenter.fetchImageUrl((Activity) getContext(), imageUrl, image, true);
    }
}
