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

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;

        import interdroid.swan.actuators.ActuatorManager;
        import interdroid.swan.actuators.activities.ParameterCheck;
        import interdroid.swan.actuators.activities.ParamterNotSpecified;

/**
 * Created by rm on 17/10/2016.
 */

public class ExpressionActuator extends Actuator {
    public static final String[] OPTIONS = new String[] {"start", "actuation", "test3"};
    public static final String ACTUATOR = "expression";
    public static final String[] REQUIRED_PARAMETERS={"exp"};

    String currentValue;
    String expressionToActuate="";

    int count=0;

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

    @Override
    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {
        int amount = (int) arguments.get(ACTUATOR);
        if(amount==0) {
            Log.d("SWANACTEXPACTUATOR","STARTED");
            if(REQUIRED_PARAMETERS[amount]!=null) {
                if (parameters.contentEquals("none")) {
                    ParamterNotSpecified paramterNotSpecified = new ParamterNotSpecified();
                    paramterNotSpecified.checkParameters(mContext, REQUIRED_PARAMETERS[amount]);
                    while (ActuatorManager.parameters.isEmpty()) {
                    }
                    expressionToActuate = ActuatorManager.parameters.get("exp");
                    ActuatorManager.parameters.clear();
                } else {
                    Log.d("SWANACTEXPACTUATOR",parameters);
                    ParameterCheck check = new ParameterCheck();
                    HashMap<String, String> returnedParameters = new HashMap<String, String>();
                    returnedParameters = check.checkParameters(mContext, REQUIRED_PARAMETERS[amount], parameters);
                    expressionToActuate = returnedParameters.get("exp");
                    //Log.d("SWANACTEXPACTUATOR",expressionToActuate);
                }
            }
            LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived,
                    new IntentFilter(trigger));
        }
    }

    public void sendExpression(String expression) {
        /// Send Registration details
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("expression-data-received");
        // You can also include some extra data.
        intent.putExtra("expression", expression);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("data");
            sendExpression(expressionToActuate);
        }
    };
}

