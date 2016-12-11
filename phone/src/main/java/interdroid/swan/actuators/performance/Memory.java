package interdroid.swan.actuators.performance;

import android.app.ActivityManager;
import android.content.Context;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

/**
 * Created by rm on 21/10/2016.
 */

public class Memory {

    @InjectDependency
    Context mContext;

    public Memory(){
        Injector.inject(Injector.getApplicationContext(), this);}

    public Double getMemoryUsage() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        double availableMegs = mi.availMem / 1048576L;
        return availableMegs;
    }

}
