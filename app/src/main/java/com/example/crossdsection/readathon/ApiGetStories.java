package com.example.crossdsection.readathon;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;

import static com.android.volley.Request.Method.GET;
import static com.example.crossdsection.readathon.ConstantApi.getStories;

/**
 * Created by chitra on 10/12/17.
 */

public class ApiGetStories {
    private static final String MODULE = "APIGETSTROIES";
    private String  strUrl = getStories;
    private Context mContext;
    private final String TAG = "ApiGetStories";


    public ApiGetStories(Context mContext){
        this.mContext = mContext;
    }

    //get Stories from server
    public void getStories(){
        JsonObjectRequest req = new JsonObjectRequest(GET, strUrl, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getLocalizedMessage());
            }
        });

        int socketTimeout = 60000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        Volley.newRequestQueue(mContext).getCache().clear();
        Volley.newRequestQueue(mContext).add(req);
    }
}
