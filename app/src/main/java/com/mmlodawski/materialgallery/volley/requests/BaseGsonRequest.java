/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.volley.requests;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueueManager;
import com.android.volley.Response;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;

public class BaseGsonRequest<T> extends GsonRequest<T> {
    private final Class<T> clazz;

    private int ttl = 0;
    private int softTtl = 0;

    public BaseGsonRequest(int method, String url, Class<T> clazz) {
        super(method, url, clazz, null, null, new GsonBuilder().create());
        this.clazz = clazz;
    }

    public BaseGsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, clazz, listener, errorListener, new GsonBuilder().create());
        this.clazz = clazz;
    }

    public BaseGsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener, Response.DispatcherListener dispatcherListener) {
        super(method, url, clazz, listener, errorListener, dispatcherListener, new GsonBuilder().create());
        this.clazz = clazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Cache.Entry<T> entry = new Cache.Entry<T>(mGson.fromJson(json, clazz), response.data, HttpHeaderParser.parseHeaders(response));

            // Set custom TTLs for network requests.
            if (getDispatcherType() == DispatcherType.NETWORK || getDispatcherType() == DispatcherType.UNKNOWN) {
                long currTime = System.currentTimeMillis();
                entry.networkHeaders.softTtl = currTime + getSoftTtl();
                entry.networkHeaders.ttl = currTime + getTtl();
            }

            return Response.success(entry);
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    public Request<T> addToQueue() {
        return RequestQueueManager.getInstance().getRequestQueue().add(this);
    }

    protected void setTtl(int ttl) {
        this.ttl = ttl;
    }

    protected int getTtl() {
        return ttl;
    }

    protected void setSoftTtl(int softTtl) {
        this.softTtl = softTtl;
    }

    protected int getSoftTtl() {
        return softTtl;
    }
}
