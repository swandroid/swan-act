package interdroid.swan.actuators.scheduler;

/**
 * Created by rm on 06/11/2016.
 */

import android.content.Context;
import android.net.TrafficStats;
import android.os.BatteryManager;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

import interdroid.swan.actuators.performance.CPU;
import interdroid.swan.actuators.performance.Memory;

/**
 * Created by rm on 28/10/2016.
 */

public class TimeMonitor {

    @InjectDependency
    Context context;

    public TimeMonitor(){
        Injector.inject(Injector.getApplicationContext(), this);
    }

    public void mointorUsage() {
        Integer level;
        Integer initialLevel;
        //double mAH=(2700 * level * 0.01);

        BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        initialLevel = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        level = initialLevel;

        long startTime = System.currentTimeMillis();

        while (1 > 0) {
            while (initialLevel == level) {
                mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
                level = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                level=level-1;
                try {
                    Thread.sleep(10000);
                }
                catch (InterruptedException ex){

                }
            }
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
        }
    }
}