/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.toolbox;

import android.util.Pair;

import com.mmlodawski.materialgallery.model.ParcelablePageModel;

public class FlickrUrlBuilder extends UrlBuilder {

    private static final String PROTOCOL = "https://";
    private static final String API_URL = "api.flickr.com/services/rest?";
    private static final String API_KEY = "d5b446a34d72c64d2e0d95f985c049b6";
    private static final String FORMAT = "json";

    public enum Method {
        SEARCH_PHOTOS {
            @Override
            public String toString() {
                return "flickr.photos.search";
            }
        }
    }

    public enum ImageSize {

        /**
         * https://www.flickr.com/services/api/misc.urls.html
         * s	small square 75x75
         * q	large square 150x150
         * t	thumbnail, 100 on longest side
         * m	small, 240 on longest side
         * n	small, 320 on longest side
         * z	medium 640, 640 on longest side
         * c	medium 800, 800 on longest side †
         * b	large, 1024 on longest side *
         * h	large 1600, 1600 on longest side †
         * k	large 2048, 2048 on longest side †
         * o	original image, either a jpg, gif or png, depending on source format
         * Before May 25th 2010 large photos only exist for very large original images.
         *
         * † Medium 800, large 1600, and large 2048 photos only exist after March 1st 2012.
         */

        AQUARE_75("s"),
        SQUARE_150("q"),
        THUMBNAIL("t"),
        SMALL_240("m"),
        SMALL_320("n"),
        MEDIUM_640("z"),
        MEDIUM_800("c"),
        LARGE_1024("b"),
        LARGE_1600("h"),
        LARGE_2048("k"),
        ORIGINAL("o");

        private String string;

        ImageSize(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }

    private Method method;
    private ParcelablePageModel pageModel;

    @Override
    public String build() {
        String page = pageModel != null ? String.valueOf(pageModel.getPageId()) : "1";
        return PROTOCOL + API_URL
                + "method=" + this.method
                + "&api_key=" + API_KEY
                + "&format=" + FORMAT
                + "&nojsoncallback=1"
                + "&page=" + page;
    }

    public FlickrUrlBuilder pageable(ParcelablePageModel pageModel) {
        this.pageModel = pageModel;
        return this;
    }

    public String buildSearchUrl(String query) {
        // https://www.flickr.com/services/api/flickr.photos.search.html

        final String searchParam = "text";

        this.method = Method.SEARCH_PHOTOS;
        String params = "&" + searchParam + "=" + query;

        return build() + params;
    }

    public static String buildImageUrl(String farm, String server, String id, String secret, ImageSize imageSize) {
        return "https://farm" + farm + ".staticflickr.com/"+ server + "/" + id + "_" + secret + "_" + imageSize.getString() + ".jpg";
    }

    public static Pair<String, String> buildImageUrlWithThumbnail(String farm, String server, String id, String secret, ImageSize imageSize) {
        String medium = buildImageUrl(farm, server, id, secret, imageSize);
        String thumbnail = buildImageUrl(farm, server, id, secret, ImageSize.THUMBNAIL);
        return new Pair<>(medium, thumbnail);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
