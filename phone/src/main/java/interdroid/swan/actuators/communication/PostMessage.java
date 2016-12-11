package interdroid.swan.actuators.communication;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rm on 25/08/16.
 */
public class PostMessage {
    RequestQueue mRequestQueue;

    public void postMessageToDevice(Context context){
        mRequestQueue= Volley.newRequestQueue(context);
        String url = "https://swan-exp-eval.herokuapp.com/postmessage";
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
                String firebTok= FirebaseInstanceId.getInstance().getToken();
                String topic="lol";
                params.put("fid", firebTok);
                params.put("topic",topic);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }
}
