package interdroid.swan.actuators;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

import interdroid.swan.actuators.jobs.Job;
import interdroid.swan.actuators.jobs.util.support.PersistableBundleCompat;

/**
 * Created by rm on 21/09/16.
 */
public class DemoSyncJob extends Job {

    @InjectDependency
    Context mContext;

    public static final String TAG = "job_demo_tag";
    public DemoSyncJob() {
        Injector.inject(Injector.getApplicationContext(), this);
    }

    @Override
    @NonNull
    protected Result onRunJob(final Params params) {
        /*Log.d("Hello Job Here","I am Executing");
        SwanService swanService=new SwanService();
        int id=swanService.registerSWANSensor(getContext(),"self@movement:x{ANY,1000}");*/


        /*final ArrayList<SwanExp> pucks = MainActivity.mSwanExpManager.getAll();
        Log.d("Pucks",pucks.get(0).toString());
        Log.d("Puck Size","lesser");
        if(pucks.size() >0) {
            Log.d("Puck Size","greater");
            Log.d("DATA:",pucks.get(0).toString());
            new Thread() {
                @Override
                public void run() {
                    Actuate.trigger(pucks.get(0), "Upcount");
                }
            }.start();
        }*/

        PersistableBundleCompat extra = params.getExtras();
        int optionselected = extra.getInt("optionselected",0);
        String expression = extra.getString("expression","LOL");
        String deviceId=extra.getString("deviceid","0");


        Intent intent = new Intent(mContext, PluginActuatorsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id",optionselected);
        extras.putString("data",expression);
        extras.putString("deviceid",deviceId);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        boolean success = new DemoSyncEngine(getContext()).sync();
        return success ? Result.SUCCESS : Result.FAILURE;
    }
}

