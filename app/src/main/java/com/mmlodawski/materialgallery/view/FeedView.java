/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.view;

import com.mmlodawski.materialgallery.model.ParcelableImageModel;

import java.util.List;

public interface FeedView extends ProgressView {

    void setItems(List<ParcelableImageModel> items);

    void addItems(List<ParcelableImageModel> items);

    void performSearch(String query);

    void showEmptyScreen();

    void hideEmptyScreen();

    void showError();
}
