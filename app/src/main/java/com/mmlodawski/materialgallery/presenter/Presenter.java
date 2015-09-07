/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.presenter;

import android.os.Bundle;

import com.mmlodawski.materialgallery.view.FeedView;

public interface Presenter {
    void setView(FeedView feedView);

    void onSaveInstanceState(Bundle outState);

    void onCreate(Bundle savedInstanceState);

    void onPerformSearch(String query);

    void onInfiniteScroll();
}
