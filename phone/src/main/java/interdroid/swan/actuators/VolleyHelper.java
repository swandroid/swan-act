package interdroid.swan.actuators;
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
public class VolleyHelper {
    RequestQueue mRequestQueue;
    public void postDeviceInfo(Context context,final HashMap<String,String> data){
        mRequestQueue= Volley.newRequestQueue(context);
        String url="";
        final HashMap<String,String> paramsextern =new HashMap<String,String>();
        for (Map.Entry<String, String> entry : data.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            if(key.contentEquals("url")){
                url=value;
            }
            else {
                paramsextern.put(key,value);
            }
        }
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
                Map<String, String> params = paramsextern ;
                return params;
            }
        };
        try {
            mRequestQueue.add(postRequest);
        }
        catch (NegativeArraySizeException ne){

        }
    }
}
