package interdroid.swan.actuators.idmanagement;

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

import java.util.HashMap;
import java.util.Map;

import interdroid.swan.actuators.ActuatorManager;

/**
 * Created by rm on 12/10/2016.
 */

public class GetId {
    public synchronized String getId(Context context ) {
        ActuatorManager.currentId=ActuatorManager.currentId+1;
        String id=ActuatorManager.selfId+","+ActuatorManager.currentId;
        return id;
    }
}