package interdroid.swan.actuators;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import interdroid.actuators.IActuatorAidl;

/**
 * Created by rm on 03/10/2016.
 */

public class RemoteService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
}

    @Override
    public IBinder onBind(Intent intent) {
        // Return the interface
        return mBinder;
    }

    private final IActuatorAidl.Stub mBinder = new IActuatorAidl.Stub() {
        public int getPid(){
            return android.os.Process.myPid();
        }
        public void register(String deviceId, int anInt, String actuator, String options, String describeActuator, String packageName, String serviceName) {
            ///Creates a new remote actuator object and inserts it
            //RemoteActuator rm=new RemoteActuator(anInt,actuator,options,describeActuator);
            //Actuator.mActuators.put(anInt,rm);
            sendMessage( deviceId, anInt,actuator,options,describeActuator,packageName,serviceName);
        }
        //Register expression and actuator to evaulate from a remote app
        public void registerExpression(String expression, String trigger, String actuator, int option, int actuatorId){
            sendExpression(expression,trigger,actuator,option,actuatorId);
        }

    };

    private void sendMessage(String deviceId, int anInt, String actuator, String options, String describeActuator, String packageName, String serviceName) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("actuator-data-received");
        // You can also include some extra data.
        intent.putExtra("id", anInt);
        intent.putExtra("actuator",actuator);
        intent.putExtra("options",options);
        intent.putExtra("describe",describeActuator);
        intent.putExtra("servicename",serviceName);
        intent.putExtra("packagename",packageName);
        intent.putExtra("deviceid",deviceId);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void sendExpression(String expression, String trigger, String actuator, int option, int actuatorId) {
        /// Send Registration details
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("expression-data-received");
        // You can also include some extra data.
        intent.putExtra("expression", expression);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }


}