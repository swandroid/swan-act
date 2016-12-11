package interdroid.swan.actuators;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.droidparts.annotation.inject.InjectDependency;

import interdroid.swan.actuators.jobs.Job;
import interdroid.swan.actuators.jobs.util.support.PersistableBundleCompat;

/**
 * Created by rm on 05/10/2016.
 */

public class RemoteActuationJob extends Job {
    @InjectDependency
    Context mContext;

    public static final String TAG = "demo";

    @Override
    @NonNull
    protected Job.Result onRunJob(final Job.Params params) {
        PersistableBundleCompat extra = params.getExtras();
        int optionselected = extra.getInt("optionselected",0);
        String expression = extra.getString("expression","lol");

        Intent intent = new Intent(mContext, PluginActuatorsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id",optionselected);
        extras.putString("data",expression);
        intent.putExtras(extras);
        mContext.startActivity(intent);


        boolean success = new DemoSyncEngine(getContext()).sync();
        return success ? Result.SUCCESS : Result.FAILURE;

    }
}
