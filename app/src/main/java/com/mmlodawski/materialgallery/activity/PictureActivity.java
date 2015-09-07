/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.Menu;
import android.view.WindowManager;

import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.view.PhotoDetailView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class PictureActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "url";
    public static final String EXTRA_COLOR = "color";

    private PhotoDetailView container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        container = (PhotoDetailView) findViewById(R.id.container_picture);
        container.onCreate(savedInstanceState);

        initActivityTransitions();

        Bundle bundle = getIntent().getExtras();
        String pictureUrl = bundle != null ? bundle.getString(EXTRA_URL) : "";
        container.setPictureUrl(pictureUrl);

        int blackColor = ContextCompat.getColor(this, R.color.black_text);
        int color = bundle != null ? bundle.getInt(EXTRA_COLOR, blackColor) : blackColor;
        setStatusBarColor(color);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        container.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        boolean handled = container.onBackPressed();
        if (!handled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            } else {
                finish();
            }
        }
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode transition = new Explode();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setSharedElementsUseOverlay(false);
            postponeEnterTransition();
        }
    }

    private void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintEnabled(true);
            systemBarTintManager.setTintColor(color);
        }
    }
}
