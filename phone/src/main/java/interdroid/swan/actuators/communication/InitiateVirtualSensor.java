package interdroid.swan.actuators.communication;

/**
 * Created by rm on 02/11/2016.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rm on 12/10/2016.
 */

public class InitiateVirtualSensor {
    RequestQueue mRequestQueue;

    public void postMessageToDevice(Context context, final String actuationType, final String completeExpression, final String data, final String senderId, final String receiverId ){
        mRequestQueue= Volley.newRequestQueue(context);
        String url = "https://swan-exp-eval.herokuapp.com/postactuator";
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
                params.put("actuationtype", actuationType);
                params.put("completeexpression",completeExpression);
                params.put("data",data);
                params.put("senderid",senderId);
                params.put("receiverid",receiverId);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

}
