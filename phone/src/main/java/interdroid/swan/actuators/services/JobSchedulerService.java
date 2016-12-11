package interdroid.swan.actuators.services;

import interdroid.swan.actuators.DemoSyncJob;
import interdroid.swan.actuators.jobs.JobRequest;
import interdroid.swan.actuators.jobs.util.support.PersistableBundleCompat;

/**
 * Created by rm on 23/09/2016.
 */
public class JobSchedulerService {

    //Example
    static String registerExpression=DemoSyncJob.TAG;

    private void testSimple() {
        PersistableBundleCompat extras = new PersistableBundleCompat();
        extras.putString("key", "Hello world");

        int jobId = new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(3_000L, 4_000L)
                .setBackoffCriteria(5_000L, JobRequest.BackoffPolicy.EXPONENTIAL)
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(false)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setExtras(extras)
                .setRequirementsEnforced(true)
                .setPersisted(true)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }
}
