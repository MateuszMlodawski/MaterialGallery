/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.toolbox;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int itemOffset;
    private boolean includeInEdges;

    public RecyclerViewItemOffsetDecoration(int spanCount, int itemOffset, boolean includeInEdges) {
        this.spanCount = spanCount;
        this.itemOffset = itemOffset;
        this.includeInEdges = includeInEdges;
    }

    public RecyclerViewItemOffsetDecoration(@NonNull Context context, int spanCount, @DimenRes int itemOffsetId, boolean includeInEdges) {
        this(spanCount, context.getResources().getDimensionPixelSize(itemOffsetId), includeInEdges);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeInEdges) {
            outRect.left = itemOffset - column * itemOffset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * itemOffset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = itemOffset;
            }
            outRect.bottom = itemOffset; // item bottom
        } else {
            outRect.left = column * itemOffset / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = itemOffset - (column + 1) * itemOffset / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = itemOffset; // item top
            }
        }
    }
}