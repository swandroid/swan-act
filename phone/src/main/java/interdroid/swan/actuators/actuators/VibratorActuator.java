package interdroid.swan.actuators.actuators;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
public class VibratorActuator extends Actuator {
    public static final String[] PATHS = new String[] {"vibrate","vibrate500","vibrate5000"};
    public static final String ACTUATOR = "vibrator";
    @Override
    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {
        int path = (int) arguments.get(ACTUATOR);
        switch (path) {
            case 0:
                LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived, new IntentFilter(trigger));
                break;
            case 1:
                LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived2, new IntentFilter(trigger));
                break;
            case 2:
                LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived3, new IntentFilter(trigger));
                break;
            default:
                Log.d("Invalid","ValuePath");
        }
    }
    Vibrator v = (Vibrator) this.mContext.getSystemService(Context.VIBRATOR_SERVICE);
    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("data").contentEquals("TRUE")){
                v.vibrate(50);
            }
            else if(intent.getStringExtra("data").contentEquals("UNREGISTER")) {
                LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mDataReceived);
            }
        }
    };
    private BroadcastReceiver mDataReceived2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("data").contentEquals("TRUE")){
                v.vibrate(500);
            }
            else if(intent.getStringExtra("data").contentEquals("UNREGISTER")) {
                LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mDataReceived2);
            }
        }
    };
    private BroadcastReceiver mDataReceived3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("data").contentEquals("TRUE")){
                v.vibrate(5000);
            }
            else if(intent.getStringExtra("data").contentEquals("UNREGISTER")) {
                LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mDataReceived3);
            }
        }
    };
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
        return PATHS;
    }
    @Override
    public String getActuator() {
        return ACTUATOR;
    }
}
