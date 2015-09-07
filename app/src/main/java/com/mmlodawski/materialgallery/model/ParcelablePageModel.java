/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ParcelablePageModel implements Parcelable {

    @SerializedName("start")
    private String startIndex;

    @SerializedName("label")
    private int pageId;

    public ParcelablePageModel() {}

    private ParcelablePageModel(Parcel in) {
        pageId = in.readInt();
        startIndex = in.readString();
    }

    public int getStartIndex() {
        return Integer.parseInt(startIndex);
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startIndex);
        dest.writeInt(pageId);
    }

    public static final Parcelable.Creator<ParcelablePageModel> CREATOR = new Parcelable.Creator<ParcelablePageModel>() {
        public ParcelablePageModel createFromParcel(Parcel in) {
            return new ParcelablePageModel(in);
        }

        public ParcelablePageModel[] newArray(int size) {
            return new ParcelablePageModel[size];
        }
    };
}
