package com.example.crossdsection.readathon.api;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.crossdsection.readathon.database.DBHelper;
import com.example.crossdsection.readathon.listeners.Listener_Success;

import static com.android.volley.Request.Method.GET;
import static com.example.crossdsection.readathon.constant.ConstantApi.getStories;

/**
 * Created by chitra on 10/12/17.
 */

public class ApiGetStories {
    private static final String MODULE = "APIGETSTROIES";
    private String  strUrl = getStories;
    private Context mContext;
    private final String TAG = "ApiGetStories";
    private Listener_Success callback;

    public ApiGetStories(Context mContext, Listener_Success callback){
        this.mContext = mContext;
        this.callback = callback;
    }

    //get Stories from server
    public void getData( final SQLiteDatabase db  ){
        JsonObjectRequest req = new JsonObjectRequest(GET, strUrl, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject object = new JSONObject(response.toString());
                        JSONArray contents = (JSONArray) object.get("content");
                        callback.success(contents.length());
                        DBHelper obj = new DBHelper( mContext );
                        obj.insertData( db, response.toString() );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.getLocalizedMessage());
                }
            }
        );

        int socketTimeout = 60000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        Volley.newRequestQueue(mContext).getCache().clear();
        Volley.newRequestQueue(mContext).add(req);
    }
}
