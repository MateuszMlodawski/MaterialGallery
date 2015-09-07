/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.volley.requests.get;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.mmlodawski.materialgallery.model.FlickrPhotosHolder;
import com.mmlodawski.materialgallery.model.ParcelablePageModel;
import com.mmlodawski.materialgallery.toolbox.FlickrUrlBuilder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SearchForFlickrImagesRequest extends BaseGetRequest<FlickrPhotosHolder> {
    private static final int RETRY_COUNT = 3;
    private static final int TIMEOUT_MS = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    private static final int SOFT_TTL = 0;
    private static final int TTL = 0;
    private static final float BACKOFF_MULT = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
    private static final Priority PRIORITY = Priority.HIGH;

    private String query;
    private ParcelablePageModel pageModel;

    public SearchForFlickrImagesRequest() {
        super("", FlickrPhotosHolder.class);
        init();
    }

    public SearchForFlickrImagesRequest(Response.Listener<FlickrPhotosHolder> listener, Response.ErrorListener errorListener) {
        super("", FlickrPhotosHolder.class, listener, errorListener);
        init();
    }

    public SearchForFlickrImagesRequest(Response.Listener<FlickrPhotosHolder> listener, Response.ErrorListener errorListener, Response.DispatcherListener dispatcherListener) {
        super("", FlickrPhotosHolder.class, listener, errorListener, dispatcherListener);
        init();
    }

    public void setNextPageModel(ParcelablePageModel pageModel) {
        if (pageModel != null) {
            this.pageModel = pageModel;
        }
    }

    public ParcelablePageModel getNexPageModel() {
        pageModel.setPageId(pageModel.getPageId() + 1);
        return pageModel;
    }

    private void init() {
        setPriority(PRIORITY);
        setSoftTtl(SOFT_TTL);
        setTtl(TTL);
        setRetryPolicy(new DefaultRetryPolicy(TIMEOUT_MS, RETRY_COUNT, BACKOFF_MULT));

        pageModel = new ParcelablePageModel();
        pageModel.setPageId(1);
    }

    @Override
    public String getUrl() {
        FlickrUrlBuilder flickrUrlBuilder = new FlickrUrlBuilder();
        flickrUrlBuilder.pageable(pageModel);
        return flickrUrlBuilder.buildSearchUrl(query);
    }

    @Override
    protected Response<FlickrPhotosHolder> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Response<FlickrPhotosHolder> r = Response.success(new Cache.Entry<>(mGson.fromJson(json, FlickrPhotosHolder.class), response.data, HttpHeaderParser.parseHeaders(response)));

            if (getDispatcherType() == DispatcherType.NETWORK || getDispatcherType() == DispatcherType.UNKNOWN) {
                long currTime = System.currentTimeMillis();
                r.cacheEntry.networkHeaders.softTtl = currTime + SOFT_TTL;
                r.cacheEntry.networkHeaders.ttl = currTime + TTL;
            }

            return r;
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("Authorization", "Client-ID 7f22d54b119fc77"); // hardcoded for this example
        return params;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
