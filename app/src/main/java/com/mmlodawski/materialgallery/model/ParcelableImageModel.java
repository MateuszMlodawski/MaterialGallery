/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ParcelableImageModel implements Parcelable {

    @SerializedName("url")
    private String url;

    @SerializedName("tbUrl")
    private String tbUrl;

    public ParcelableImageModel(String url, String thumbnailUrl) {
        this.url = url;
        this.tbUrl = thumbnailUrl;
    }

    private ParcelableImageModel(Parcel in) {
        tbUrl = in.readString();
        url = in.readString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(tbUrl);
    }

    public static final Parcelable.Creator<ParcelableImageModel> CREATOR = new Parcelable.Creator<ParcelableImageModel>() {
        public ParcelableImageModel createFromParcel(Parcel in) {
            return new ParcelableImageModel(in);
        }

        public ParcelableImageModel[] newArray(int size) {
            return new ParcelableImageModel[size];
        }
    };
}
