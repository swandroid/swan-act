package interdroid.swan.actuators.actuators;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.json.JSONException;
        import org.json.JSONObject;

import interdroid.swan.actuators.communication.RemoteDeviceActuatorComm;


/**
 * Created by rm on 15/10/2016.
 */

public class VirtualSensorActuator extends Actuator {
    public static final String[] OPTIONS = new String[]{"remote","pop"};
    public static final String ACTUATOR = "virtualsensor";

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

    String expressionId;

    String sendingDevice;

    String receivingDevice;

    String trigger;

    RemoteDeviceActuatorComm remoteDev = new RemoteDeviceActuatorComm();

    @Override
    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {
        this.expressionId=expressionId;
        this.sendingDevice=sendingDevice;
        this.receivingDevice=receivingDevice;
        this.trigger=trigger;

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived, new IntentFilter(trigger));

    }
    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
                remoteDev.postMessageToDevice(mContext, "localswanremoteact", trigger+"*"+expressionId,intent.getStringExtra("data"), sendingDevice, receivingDevice);
        }
    };
}


