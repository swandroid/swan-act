package interdroid.swan.actuators;

import interdroid.swan.actuators.jobs.Job;
import interdroid.swan.actuators.jobs.JobCreator;

/**
 * @author rwondratschek
 */
public class DemoJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob();
            case ActuatorJobScheduler.TAG:
                return new ActuatorJobScheduler();
            case RemoteDeviceJobScheduler.TAG:
                return new RemoteDeviceJobScheduler();
            case VirtualSensorCreatorScheduler.TAG:
                return new VirtualSensorCreatorScheduler();
            default:
                return null;
        }
    }
}
