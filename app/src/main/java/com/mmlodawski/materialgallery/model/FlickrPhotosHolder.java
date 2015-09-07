/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.model;

import com.google.gson.annotations.SerializedName;

public class FlickrPhotosHolder {

    @SerializedName("photos")
    private FlickrPhotos flickrPhotos;

    public FlickrPhotos getFlickrPhotos() {
        return flickrPhotos;
    }

    public void setFlickrPhotos(FlickrPhotos flickrPhotos) {
        this.flickrPhotos = flickrPhotos;
    }
}
