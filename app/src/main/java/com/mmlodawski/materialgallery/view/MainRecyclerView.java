/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.adapter.FeedAdapter;
import com.mmlodawski.materialgallery.model.ParcelableImageModel;
import com.mmlodawski.materialgallery.toolbox.RecyclerViewItemOffsetDecoration;

import java.util.List;

public class MainRecyclerView extends RecyclerView {

    private FeedAdapter feedAdapter;

    public MainRecyclerView(Context context) {
        super(context);
    }

    public MainRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        // Get number of columns from xml
        int spansCount = getContext().getResources().getInteger(R.integer.grid_span);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spansCount);
        feedAdapter = new FeedAdapter(getContext());
        setLayoutManager(gridLayoutManager);
        addItemDecoration(new RecyclerViewItemOffsetDecoration(getContext(), spansCount, R.dimen.grid_cell_offset, false));
        setAdapter(feedAdapter);
        scheduleLayoutAnimation();
    }

    /**
     * Sets items with witch RecyclerView will be filled
     * @param imageModels items to be set
     */
    public void setItems(List<ParcelableImageModel> imageModels) {
        feedAdapter.setItems(imageModels);
    }

    /**
     * Adds items to RecyclerView
     * @param imageModels items to be added
     */
    public void addItems(List<ParcelableImageModel> imageModels) {
        feedAdapter.addItems(imageModels);
    }
}
