/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.mmlodawski.materialgallery.toolbox.DialogHelper;

public abstract class ProgressLayout extends FrameLayout implements ProgressView {

    private Dialog dialogProgress;

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void showProgress() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            return;
        }
        dialogProgress = DialogHelper.createProgressDialogAndShow(getContext());
    }

    @Override
    public void hideProgress() {
        if (dialogProgress != null && dialogProgress.isShowing()) {
            dialogProgress.dismiss();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        hideProgress();
    }
}
