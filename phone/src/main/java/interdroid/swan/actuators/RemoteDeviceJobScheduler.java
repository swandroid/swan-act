package interdroid.swan.actuators;

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
import interdroid.swan.actuators.performance.Memory;
import interdroid.swan.actuators.swan.SwanSensor;

import static interdroid.swan.actuators.ActuatorManager.mSwanExpManager;

/**
 * Created by rm on 15/10/2016.
 */

public class RemoteDeviceJobScheduler extends Job {
    @InjectDependency
    Context mContext;

    public static final String TAG = "remote_device_scheduler_job";
    String trigger;
    String expression;
    String actuationType;

    public RemoteDeviceJobScheduler() {
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

                SwanSensor swanSensorService = new SwanSensor();
                int idToDeregister = swanSensorService.registerSWANSensor(mContext, expression, trigger);
                ActuatorManager.expressions.put(expression, swanSensorService);
                Integer size = ActuatorManager.expressions.size();
                Log.d("ZENITH", size.toString());



            new Thread() {
                @Override
                public void run() {
                    for (int k = 0; k < rules.size(); k++) {
                        Actuate.trigger(rules.get(k), trigger);
                    }
                }
            }.start();


        //Performance Parameters
        Memory after=new Memory();
        Log.d("memory after"+trigger,Double.toString(after.getMemoryUsage()));

        boolean success = new DemoSyncEngine(getContext()).sync();
        return success ? Result.SUCCESS : Result.FAILURE;
    }
}
