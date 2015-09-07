/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.toolbox;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class InfiniteRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    // Default offset
    private int offset = 2;
    private int previousTotal = 0;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private boolean loading = true;
    private boolean layoutManagerNotSupported;

    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;

    public InfiniteRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            mGridLayoutManager = (GridLayoutManager) layoutManager;
        } else if (layoutManager instanceof LinearLayoutManager) {
            mLinearLayoutManager = (LinearLayoutManager) layoutManager;
        } else {
            layoutManagerNotSupported = true;
            Log.e("InfiniteScrollListener", "LayoutManager no supported.");
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (layoutManagerNotSupported) return;

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = getItemCount();
        firstVisibleItem = findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + getOffset())) {
            onInfiniteScroll();
            loading = true;
        }
    }

    public abstract void onInfiniteScroll();

    private int getItemCount() {
        return mLinearLayoutManager != null ? mLinearLayoutManager.getItemCount() : mGridLayoutManager.getItemCount();
    }

    private int findFirstVisibleItemPosition() {
        return mLinearLayoutManager != null ? mLinearLayoutManager.findFirstVisibleItemPosition() : mGridLayoutManager.findFirstVisibleItemPosition();
    }

    private int getOffset() {
        // For GridLayoutManager multiply offset by span count
        return mLinearLayoutManager != null ? offset : offset * mGridLayoutManager.getSpanCount();
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getFirstVisibleItem() {
        return firstVisibleItem;
    }

    public void setFirstVisibleItem(int firstVisibleItem) {
        this.firstVisibleItem = firstVisibleItem;
    }
}
