/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlickrPhotos {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("perpage")
    private int perPage;

    @SerializedName("total")
    private String total;

    @SerializedName("photo")
    private List<FlickrPhoto> photos;

    public void setPage(int page) {
        this.page = page;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPhotos(List<FlickrPhoto> photos) {
        this.photos = photos;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public String getTotal() {
        return total;
    }

    public List<FlickrPhoto> getPhotos() {
        return photos;
    }
}
