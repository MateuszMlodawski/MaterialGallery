/**
 * Author Mateusz Mlodawski mmlodawski@gmail.com
 */
package com.mmlodawski.materialgallery.volley.requests.get;

import com.android.volley.Request;
import com.android.volley.Response;
import com.mmlodawski.materialgallery.volley.requests.BaseGsonRequest;

public class BaseGetRequest<T> extends BaseGsonRequest<T> {

    public BaseGetRequest(String url, Class<T> clazz) {
        super(Request.Method.GET, url, clazz);
    }

    public BaseGetRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, url, clazz, listener, errorListener);
    }

    public BaseGetRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener, Response.DispatcherListener dispatcherListener) {
        super(Request.Method.GET, url, clazz, listener, errorListener, dispatcherListener);
    }
}