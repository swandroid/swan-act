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
 import interdroid.swan.actuators.swan.FeedBackSwanService;
/**
 * Created by rm on 19/10/2016.
 */

public class FeedBackActuator extends Actuator {
    public static final String[] OPTIONS = new String[] {"before", "actuation", "test3"};
    public static final String ACTUATOR = "feedback";
    public static final String[] REQUIRED_PARAMETERS={"expression,window"};

    String currentValue;
    String expressionToFeedback="";
    String window="";
    String comparator="";

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
            Log.d("FEEDBACK","STARTED");
            LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived,
                    new IntentFilter(trigger));

            if(REQUIRED_PARAMETERS[amount]!=null) {
                if (parameters.contentEquals("none")) {
                    ParamterNotSpecified paramterNotSpecified = new ParamterNotSpecified();
                    paramterNotSpecified.checkParameters(mContext, REQUIRED_PARAMETERS[amount]);
                    while (ActuatorManager.parameters.isEmpty()) {
                    }
                    expressionToFeedback = ActuatorManager.parameters.get("expression");
                    window = ActuatorManager.parameters.get("window");
                    ActuatorManager.parameters.clear();
                } else {
                    ParameterCheck check = new ParameterCheck();
                    HashMap<String, String> returnedParameters = new HashMap<String, String>();
                    returnedParameters = check.checkParameters(mContext, REQUIRED_PARAMETERS[amount], parameters);
                    expressionToFeedback = returnedParameters.get("expression");
                    window = returnedParameters.get("window");

                }
            }
        }
    }

    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            FeedBackSwanService feedBackSwanService=new FeedBackSwanService();
            feedBackSwanService.changeParameters(mContext,expressionToFeedback,window);
        }
    };
}

