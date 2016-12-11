package interdroid.swan.actuators;

/**
 * Created by rm on 03/11/2016.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SendSensorToBuilder {
    RequestQueue mRequestQueue;

    public void postMessageToDevice(Context context, final String name, final ArrayList<String> valuePaths){
        mRequestQueue= Volley.newRequestQueue(context);
        String valuePaths2="";
        for(int i=0;i<valuePaths.size();i++){
            valuePaths2=valuePaths2+valuePaths.get(i)+",";
        }
        final String valuePathsToSend=valuePaths2;
        String url = "https://swan-exp-eval.herokuapp.com/getsensors";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        String erro ="volley error";
                        Log.d("Error.Response", erro);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("valuepaths",valuePathsToSend);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

}

