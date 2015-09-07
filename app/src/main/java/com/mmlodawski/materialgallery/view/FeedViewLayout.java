/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.model.ParcelableImageModel;
import com.mmlodawski.materialgallery.presenter.FeedPresenter;
import com.mmlodawski.materialgallery.toolbox.InfiniteRecyclerViewScrollListener;

import java.util.List;

public class FeedViewLayout extends ProgressLayout implements FeedView {

    private static final String KEY_RECYCLER_VIEW_POSITION = "recycler_view_position";

    private MainRecyclerView mainRecyclerView;
    private TextView textView;
    private FeedPresenter feedPresenter;
    private InfiniteRecyclerViewScrollListener infiniteRecyclerViewScrollListener;

    public FeedViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        feedPresenter = new FeedPresenter();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mainRecyclerView = (MainRecyclerView) findViewById(R.id.recycler_view_main);
        textView = (TextView) findViewById(R.id.text_view_empty_screen);
        infiniteRecyclerViewScrollListener = new InfiniteRecyclerViewScrollListener(mainRecyclerView.getLayoutManager()) {
            @Override
            public void onInfiniteScroll() {
                feedPresenter.onInfiniteScroll();
            }
        };
        mainRecyclerView.addOnScrollListener(infiniteRecyclerViewScrollListener);

        feedPresenter.setView(this);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void setItems(List<ParcelableImageModel> items) {
        mainRecyclerView.setItems(items);
    }

    @Override
    public void addItems(List<ParcelableImageModel> items) {
        mainRecyclerView.addItems(items);
    }

    @Override
    public void performSearch(String query) {
        feedPresenter.onPerformSearch(query);
    }

    @Override
    public void showEmptyScreen() {
        textView.setText(getResources().getText(R.string.error_no_results));
        textView.setVisibility(View.VISIBLE);
        mainRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyScreen() {
        textView.setVisibility(View.GONE);
        mainRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getResources().getText(R.string.error_internet_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save RecyclerView position
        outState.putInt(KEY_RECYCLER_VIEW_POSITION, infiniteRecyclerViewScrollListener.getFirstVisibleItem());

        feedPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        feedPresenter.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Retrieve RecyclerView position and scroll the RecyclerView
            int position = savedInstanceState.getInt(KEY_RECYCLER_VIEW_POSITION);
            mainRecyclerView.scrollToPosition(position);
            infiniteRecyclerViewScrollListener.setFirstVisibleItem(position);
        }
    }
}
