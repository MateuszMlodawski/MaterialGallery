/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mmlodawski.materialgallery.R;
import com.mmlodawski.materialgallery.activity.PictureActivity;
import com.mmlodawski.materialgallery.listeners.OnPictureClickListener;
import com.mmlodawski.materialgallery.model.ParcelableImageModel;

import java.util.ArrayList;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final Context context;
    private final List<ParcelableImageModel> imageModels;
    private ViewGroup parent;

    public FeedAdapter(Context context) {
        this.context = context;
        this.imageModels = new ArrayList<>();
    }

    public FeedAdapter(Context context, List<ParcelableImageModel> imageModels) {
        this.context = context;
        this.imageModels = imageModels;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view, parent, false);
        this.parent = parent;
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ParcelableImageModel imageModel = imageModels.get(position);

        Glide.with(context)
                .load(imageModel.getUrl())
                .asBitmap()
                .placeholder(R.color.light_green_100)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        new Palette.Builder(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                // Get colors from Palette
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();
                                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();

                                int color = 0;
                                // Pick darkVibrant if generated, if not try darkMuted
                                if (darkVibrantSwatch != null) {
                                    color = darkVibrantSwatch.getRgb();
                                    holder.ripple.setRippleColor(color);
                                } else if (darkMutedSwatch != null) {
                                    color = darkMutedSwatch.getRgb();
                                    holder.ripple.setRippleColor(color);
                                }

                                if (color != 0) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        // We can do Activity Transitions with Lollipop and higher
                                        holder.ripple.setOnClickListener(
                                                new OnPictureClickListener((Activity) parent.getContext(), PictureActivity.class, imageModel.getUrl(), holder.image, color)
                                        );
                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        // We can't do Activity transitions. Pass only color.
                                        holder.ripple.setOnClickListener(
                                                new OnPictureClickListener((Activity) parent.getContext(), PictureActivity.class, imageModel.getUrl(), null, color)
                                        );
                                    }
                                }
                            }
                        });
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.ripple.setOnClickListener(
                    new OnPictureClickListener((Activity) parent.getContext(), PictureActivity.class, imageModel.getUrl(), holder.image)
            );
        } else {
            holder.ripple.setOnClickListener(
                    new OnPictureClickListener((Activity) parent.getContext(), PictureActivity.class, imageModel.getUrl())
            );
        }
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }

    /**
     * Clears the adapter and sets new dataset
     * @param imageModels items to set
     */
    public void setItems(List<ParcelableImageModel> imageModels) {
        this.imageModels.clear();
        this.imageModels.addAll(imageModels);
        notifyDataSetChanged();
    }

    /**
     * Adds items to current dataset
     * @param imageModels items to add
     */
    public void addItems(List<ParcelableImageModel> imageModels) {
        this.imageModels.addAll(imageModels);
        notifyDataSetChanged();
    }

    /**
     * Provides a reference to the views for each data item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialRippleLayout ripple;
        final ImageView image;

        public ViewHolder(View v) {
            super(v);
            ripple = (MaterialRippleLayout) v.findViewById(R.id.ripple);
            image = (ImageView) v.findViewById(R.id.grid_view_row_image);
        }
    }
}
