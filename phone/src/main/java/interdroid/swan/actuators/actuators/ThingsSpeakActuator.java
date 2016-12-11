package interdroid.swan.actuators.actuators;

/**
 * Created by rm on 29/11/2016.
 */

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.support.v4.content.LocalBroadcastManager;
        import android.util.Log;

        import interdroid.swan.actuators.ActuatorManager;
        import interdroid.swan.actuators.VolleyHelper;
        import interdroid.swan.actuators.activities.ParameterCheck;
        import interdroid.swan.actuators.activities.ParamterNotSpecified;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;

/**
 * Created by rm on 29/09/2016.
 */
public class ThingsSpeakActuator extends Actuator {
    public static final String[] OPTIONS = new String[] {"put", "post", "delete"};
    public static final String ACTUATOR = "ThingsSpeak";
    public static final String[] REQUIRED_PARAMETERS={"api_key,field"};

    VolleyHelper volleyHelper =new VolleyHelper();

    public static int id;
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int idToSet){
        id=idToSet;
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public String getActuator() {
        return ACTUATOR;
    }

    public static String api_key="";
    public static String field="";


    @Override
    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {


        int path = (int) arguments.get(ACTUATOR);
        if(path==0) {
            if(REQUIRED_PARAMETERS[path]!=null) {
                if (parameters.contentEquals("none")) {
                    ParamterNotSpecified paramterNotSpecified = new ParamterNotSpecified();
                    paramterNotSpecified.checkParameters(mContext, REQUIRED_PARAMETERS[path]);
                    while (ActuatorManager.parameters.isEmpty()) {
                    }
                    api_key = ActuatorManager.parameters.get("api_key");
                    field = ActuatorManager.parameters.get("field");
                    ActuatorManager.parameters.clear();
                } else {
                    Log.d("SWANACTEXPACTUATOR",parameters);
                    ParameterCheck check = new ParameterCheck();
                    HashMap<String, String> returnedParameters = new HashMap<String, String>();
                    returnedParameters = check.checkParameters(mContext, REQUIRED_PARAMETERS[path], parameters);
                    //Log.d("SWANACTEXPACTUATOR",api_key);
                    //Log.d("SWANACTEXPACTUATOR",field);
                    api_key = returnedParameters.get("api_key");
                    field = returnedParameters.get("field");
                }
            }
            MyReceiver myReceiver = new MyReceiver(api_key,field);
            LocalBroadcastManager.getInstance(mContext).registerReceiver(myReceiver,
                    new IntentFilter(trigger));
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        String api_key="";
        String field="";
        public MyReceiver(String api_key,String field) {
            this.api_key=api_key;
            this.field=field;
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            HashMap<String,String> dataToPost=new HashMap<String,String>();
            dataToPost.put("url","https://api.thingspeak.com/update");
            dataToPost.put("api_key",api_key);
            dataToPost.put(field,data);
            volleyHelper.postDeviceInfo(mContext,dataToPost);        }
    }


}

