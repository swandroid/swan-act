package interdroid.swan.actuators;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

import java.util.Random;

import interdroid.swan.R;
import interdroid.actuators.IActuationResult;
import interdroid.swan.actuators.swan.SwanSensor;
import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SwanException;
import interdroid.swancore.swanmain.ValueExpressionListener;
import interdroid.swancore.swansong.ExpressionFactory;
import interdroid.swancore.swansong.ExpressionParseException;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.ValueExpression;

/**
 * Created by rm on 05/10/2016.
 */

public class PluginActuatorsActivity extends Activity {

    IActuationResult dataService;
    DataConnection conn;
    String expression="";
    String trigger="";

    class DataConnection implements ServiceConnection {
        /** is called once the bind succeeds */
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataService = IActuationResult.Stub.asInterface(service);
            Log.d("Bound to data : ",dataService.toString());
            sendData();
        }
        /*** is called once the remote service is no longer available */
        public void onServiceDisconnected(ComponentName name) { //
            dataService = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        String expressionReceived = extras.getString("expression");
        String triggerReceived = extras.getString("trigger");

        this.expression=expressionReceived;
        this.trigger=triggerReceived;

        //setContentView(R.layout.activity_discover);

        conn = new DataConnection();
        // name must match the service's Intent filter in the Service Manifest file
        // get as parameters
        Intent intent = new Intent("thingsspeakservice");
        intent.setPackage("com.example.rm.swanthingsspeakplugin");
        // bind to the Service, create it if it's not already there
        bindService(intent, conn, BIND_AUTO_CREATE);

    }
    public void sendData(){
       /* SwanSensor local = ActuatorManager.expressions.get(expression);
        while(!local.checkRegistrationStatus().contentEquals(trigger)) {
            //local.getValue();
           if(local.getValue()!=null){
                if(local.getValue().contentEquals("TRUE")){
                    String localValue=local.getValue();
                    try {
                        dataService.onData(0, localValue);
                    }
                    catch (RemoteException re){

                    }
                }
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        dataService = null;
    }

}
