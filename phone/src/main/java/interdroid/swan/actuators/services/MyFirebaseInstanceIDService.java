package interdroid.swan.actuators.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rm on 08/07/16.
 */
//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    RequestQueue mRequestQueue;

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //postDeviceInfo(refreshedToken);

    }

    public void postDeviceInfo(final String refreshedToken){
        mRequestQueue= Volley.newRequestQueue(this);
        String url = "https://swan-exp-eval.herokuapp.com/authentication";
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
                //String firebTok= FirebaseInstanceId.getInstance().getToken();
                //String topic="lol";
                String emailId="rsm1";
                params.put("email",emailId);
                params.put("fbase", refreshedToken);
                return params;
            }
        };
        mRequestQueue.add(postRequest);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}