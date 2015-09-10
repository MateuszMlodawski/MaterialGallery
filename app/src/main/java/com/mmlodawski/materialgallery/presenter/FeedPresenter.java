/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mmlodawski.materialgallery.model.FlickrPhoto;
import com.mmlodawski.materialgallery.model.FlickrPhotosHolder;
import com.mmlodawski.materialgallery.model.ParcelableImageModel;
import com.mmlodawski.materialgallery.model.ParcelablePageModel;
import com.mmlodawski.materialgallery.toolbox.FlickrUrlBuilder;
import com.mmlodawski.materialgallery.view.FeedView;
import com.mmlodawski.materialgallery.volley.requests.get.SearchForFlickrImagesRequest;

import java.util.ArrayList;
import java.util.List;

public class FeedPresenter implements FeedPresenterInterface {

    private static final String KEY_DISPLAYED_ITEMS = "displayed_items";
    private static final String KEY_NEXT_PAGE_MODEL = "next_page_model";
    private static final String KEY_RECENT_QUERY = "recent_query";

    private FeedView feedView;
    private ParcelablePageModel nextPageModel;
    private String recentQuery;
    private boolean isLoadingMoreItems;
    private ArrayList<ParcelableImageModel> displayedItems;
    private SearchForFlickrImagesRequest searchForFlickrImagesRequest;

    @Override
    public void setView(FeedView feedView) {
        this.feedView = feedView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_DISPLAYED_ITEMS, displayedItems);
        outState.putParcelable(KEY_NEXT_PAGE_MODEL, nextPageModel);
        outState.putString(KEY_RECENT_QUERY, recentQuery);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            displayedItems = savedInstanceState.getParcelableArrayList(KEY_DISPLAYED_ITEMS);
            nextPageModel = savedInstanceState.getParcelable(KEY_NEXT_PAGE_MODEL);
            recentQuery = savedInstanceState.getString(KEY_RECENT_QUERY);
            if (displayedItems != null) {
                feedView.setItems(displayedItems);
                if (displayedItems.size() > 0) {
                    feedView.hideEmptyScreen();
                } else {
                    feedView.showEmptyScreen();
                }
            }
        }
    }

    @Override
    public void onPerformSearch(@NonNull String query) {
        if (!isQueryTheSameAsPrevious(query)) {
            loadItems(query);
            recentQuery = query;
        }
    }

    @Override
    public void onInfiniteScroll() {
        if (!isLoadingMoreItems && hasNextPage()) {
            loadMoreItems(recentQuery);
        }
    }

    /**
     * Launch request and pass items to {@link FeedView} replacing the old dataset
     * @param query
     */
    private void loadItems(String query) {
        feedView.showProgress();

        searchForFlickrImagesRequest = new SearchForFlickrImagesRequest(
                new Response.Listener<FlickrPhotosHolder>() {
                    @Override
                    public void onResponse(FlickrPhotosHolder response) {
                        List<ParcelableImageModel> imageModels = new ArrayList<>();

                        // Convert FlickrPhoto objects to ImageModel objects
                        for (FlickrPhoto galleryAlbum : response.getFlickrPhotos().getPhotos()) {
                            Pair<String, String> urlPair = FlickrUrlBuilder.buildImageUrlWithThumbnail(
                                    String.valueOf(galleryAlbum.getFarm()),
                                    galleryAlbum.getServer(),
                                    galleryAlbum.getId(),
                                    galleryAlbum.getSecret(),
                                    FlickrUrlBuilder.ImageSize.MEDIUM_640
                            );
                            imageModels.add(new ParcelableImageModel(urlPair.first, urlPair.second));
                        }

                        updateDisplayedItems(imageModels, true);

                        if (imageModels.size() == 0) {
                            feedView.showEmptyScreen();
                            nextPageModel = null;
                        } else {
                            nextPageModel = searchForFlickrImagesRequest.getNexPageModel();
                            feedView.hideEmptyScreen();
                        }

                        feedView.setItems(imageModels);
                        feedView.hideProgress();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        feedView.hideProgress();
                        feedView.showError();
                        recentQuery = null;
                    }
                }
        );

        searchForFlickrImagesRequest.setTag(this);
        searchForFlickrImagesRequest.setQuery(query);
        searchForFlickrImagesRequest.setNextPageModel(nextPageModel);
        searchForFlickrImagesRequest.addToQueue();
    }

    /**
     * Launch request and pass items to {@link FeedView} adding them to the old dataset
     * @param query
     */
    private void loadMoreItems(String query) {
        isLoadingMoreItems = true;

        searchForFlickrImagesRequest = new SearchForFlickrImagesRequest(
                new Response.Listener<FlickrPhotosHolder>() {
                    @Override
                    public void onResponse(FlickrPhotosHolder response) {
                        List<ParcelableImageModel> imageModels = new ArrayList<>();

                        // Convert FlickrPhoto objects to ImageModel objects
                        for (FlickrPhoto galleryAlbum : response.getFlickrPhotos().getPhotos()) {
                            Pair<String, String> urlPair = FlickrUrlBuilder.buildImageUrlWithThumbnail(
                                    String.valueOf(galleryAlbum.getFarm()),
                                    galleryAlbum.getServer(),
                                    galleryAlbum.getId(),
                                    galleryAlbum.getSecret(),
                                    FlickrUrlBuilder.ImageSize.MEDIUM_640
                            );
                            imageModels.add(new ParcelableImageModel(urlPair.first, urlPair.second));
                        }

                        updateDisplayedItems(imageModels, false);
                        feedView.addItems(imageModels);
                        nextPageModel = searchForFlickrImagesRequest.getNexPageModel();
                        isLoadingMoreItems = false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        isLoadingMoreItems = false;
                    }
                }
        );

        searchForFlickrImagesRequest.setTag(this);
        searchForFlickrImagesRequest.setQuery(query);
        searchForFlickrImagesRequest.setNextPageModel(nextPageModel);
        searchForFlickrImagesRequest.addToQueue();
    }

    /**
     * Checks whether the current query is the same as previous
     * @param query query to check
     * @return true if query is the same as previous
     */
    private boolean isQueryTheSameAsPrevious(@NonNull String query) {
        return recentQuery != null && recentQuery.equals(query);
    }

    /**
     * Checks if next page can be requested
     * @return true if next page can be requested
     */
    private boolean hasNextPage() {
        return nextPageModel != null;
    }

    /**
     * Updates locally stored dataset
     * @param imageModels items to add to the dataset
     * @param clear true if the dataset should be cleared before adding new items
     */
    private void updateDisplayedItems(List<ParcelableImageModel> imageModels, boolean clear) {
        if (displayedItems == null) displayedItems = new ArrayList<>();
        if (clear) displayedItems.clear();
        displayedItems.addAll(imageModels);
    }
}
