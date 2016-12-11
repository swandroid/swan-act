package interdroid.swan.actuators;

/**
 * Created by rm on 03/11/2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

import java.util.ArrayList;

import interdroid.swan.actuators.actuation.Actuate;
import interdroid.swan.actuators.entities.SwanExp;
import interdroid.swan.actuators.jobs.Job;
import interdroid.swan.actuators.jobs.util.support.PersistableBundleCompat;
import interdroid.swan.actuators.swan.SwanSensor;

import static interdroid.swan.actuators.ActuatorManager.mSwanExpManager;

/**
 * Created by rm on 15/10/2016.
 */

public class VirtualSensorCreatorScheduler extends Job {
    @InjectDependency
    Context mContext;
    public static final String TAG = "virtual_sensor_creator_scheduler_job";
    String trigger;
    String expression;
    String actuationType;
    public VirtualSensorCreatorScheduler() {
        Injector.inject(Injector.getApplicationContext(), this);
    }
    @Override
    @NonNull
    protected synchronized Job.Result onRunJob(final Job.Params params) {

        PersistableBundleCompat extras = params.getExtras();
        trigger = extras.getString("trigger","lol");
        expression = extras.getString("expression","lol");
        actuationType=extras.getString("actuationtype","lol");
        final ArrayList<SwanExp> rules = mSwanExpManager.getAll();

        String [] expressionFrom=trigger.split(",");
        String expressionLocation= expressionFrom[0];
        expression=expressionLocation+expression;
        //expression = "virtual" + expression;


       /* new Thread() {
            @Override
            public void run() {
                SwanSensor swanVirtualSensorService = new SwanSensor();
                int idToDeregister = swanVirtualSensorService.registerVirtualSensor(mContext, expression, trigger);
                ActuatorManager.expressions.put(expression, swanVirtualSensorService);
                Integer size = ActuatorManager.expressions.size();
            }
        }.start();*/



        /*new Thread() {
            @Override
            public void run() {*/
                if(ActuatorManager.runningExpressions.get(expression)==null) {
                    SwanSensor swanSensorService = new SwanSensor();
                    int idToDeregister = swanSensorService.registerVirtualSensor(mContext, expression, trigger);
                    ActuatorManager.expressions.put(expression, swanSensorService);
                    Integer size = ActuatorManager.expressions.size();
                    ActuatorManager.runningExpressionsCount.put(expression,0);
                    //swanSensorService.registerMultipleForSameExpression(trigger);
                }
                else{
                    ///Add Expression Count
                    Integer expressionCount=ActuatorManager.runningExpressionsCount.get(expression);
                    ActuatorManager.runningExpressionsCount.remove(expression);
                    ActuatorManager.runningExpressionsCount.put(expression,expressionCount+1);

                    SwanSensor local=ActuatorManager.expressions.get(expression);
                    local.registerMultipleForSameExpression(trigger);
                }
          /*  }
        }.start();*/

        new Thread() {
                @Override
                public void run() {
                    for (int k = 0; k < rules.size(); k++) {
                        if(rules.get(k).getName().contentEquals(expression)) {
                            Log.d("ZENITH VIRTUAL",rules.get(k).toString());
                            Actuate.trigger(rules.get(k), trigger);
                        }
                    }
                }
        }.start();

        //Performance Parameters

        boolean success = new DemoSyncEngine(getContext()).sync();
        return success ? Result.SUCCESS : Result.FAILURE;
    }
}
