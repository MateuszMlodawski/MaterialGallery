/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.presenter;

import android.os.Bundle;

import com.mmlodawski.materialgallery.view.PhotoDetailView;

public interface PhotoDetailPresenterInterface {

    void setView(PhotoDetailView pictureView);

    void onSaveInstanceState(Bundle outState);

    void onCreate(Bundle savedInstanceState);
}
