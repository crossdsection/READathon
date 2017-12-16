package com.example.crossdsection.readathon.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crossdsection.readathon.constant.ConstantApi;
import com.example.crossdsection.readathon.constant.Constants;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chitra on 16/12/17.
 */

public class ApiGetMeaning {
    public static final String MODULE = "APIGETMEANING";
    public static String Str_Url = ConstantApi.getDictionary;
    public Context context;

    public ApiGetMeaning(Context context){
        this.context = context;
    }

    public void getMeaningOfWord(String word){
        String language = "en";
        String word_id = word.toLowerCase();
        Str_Url = Str_Url + "/entries/" + language + "/" + word_id;

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, Str_Url, null
                , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
                headers.put(Constants.APP_KEY, ConstantApi.APP_KEY);
                headers.put(Constants.APP_ID, ConstantApi.APP_ID);
                return headers;
            }
        };

        int socketTimeout = 60000;// 30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);

        Volley.newRequestQueue(context).getCache().clear();
        Volley.newRequestQueue(context).add(req);
    }
}
