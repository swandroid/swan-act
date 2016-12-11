package interdroid.swan.actuators.actuators;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import interdroid.actuators.IActuationResult;
import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by rm on 16/10/2016.
 */

public class PluginActuator extends Actuator {
    public String actuator;
    public String describeActuator;
    public int id = 1;
    public String[] options;
    public String packageName;
    public String serviceName;
    public String deviceId;

    IActuationResult dataService;
    DataConnection conn;

    public PluginActuator(String deviceId, int id, String actuator, String options, String describeActuator, String packageName, String serviceName) {
        this.options = options.split(",");
        this.actuator = actuator;
        this.describeActuator = describeActuator;
        this.id = id;
        this.packageName = packageName;
        this.serviceName = serviceName;
        this.deviceId = deviceId;
    }


    public static int idd;
    @Override
    public int getId() {
        return idd;
    }
    @Override
    public void setId(int idToSet){
        idd=idToSet;
    }

    @Override
    public String[] getOptions() {
        return options;
    }

    @Override
    public String getActuator() {
        return actuator;
    }

    String expressionToAct;

    String triggerToAct;

    @Override
    void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {

        LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived,
                new IntentFilter(trigger));

        conn = new DataConnection();
        // name must match the service's Intent filter in the Service Manifest file
        // get as parameters
        Intent intent = new Intent(this.serviceName);
        intent.setPackage(this.packageName);
        // bind to the Service, create it if it's not already there
        mContext.getApplicationContext().bindService(intent, conn, BIND_AUTO_CREATE);
        this.expressionToAct=expressionId;
        this.triggerToAct=trigger;

    }

    class DataConnection implements ServiceConnection {
        /** is called once the bind succeeds */
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataService = IActuationResult.Stub.asInterface(service);
            Log.d("Bound to data : ",dataService.toString());
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    sendData();

                }
            }).start();*/
        }
        /*** is called once the remote service is no longer available */
        public void onServiceDisconnected(ComponentName name) { //
            dataService = null;
        }
    }

    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("data");
            try {
                dataService.onData(0, data);
            }
            catch (RemoteException re){
            }
        }
    };

}
