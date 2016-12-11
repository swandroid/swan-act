package interdroid.swan.actuators.performance;

import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.BatteryManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

/**
 * Created by rm on 28/10/2016.
 */

public class Monitor {

    @InjectDependency
    Context context;

    public Monitor(){
        Injector.inject(Injector.getApplicationContext(), this);
    }

    public void mointorUsage() {

        String expression = "self@light:lux{ANY,50ms}<10.0THENself@socket:post";
        Integer level;
        Integer initialLevel;
        //double mAH=(2700 * level * 0.01);

        while (1 > 0) {

            BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        initialLevel = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        level = initialLevel;

        Memory memoryCheck = new Memory();
        Double memoryCheckBefore = memoryCheck.getMemoryUsage();

        CPU cpu=new CPU();
        Float CPUBefore=cpu.readUsage();
        //Log.d("LEVEL CPU",CPUBefore.toString());
        Integer cores=cpu.getNumberOfCores();
        Log.d("LEVEL CORES",cores.toString());


        long mStarRX = TrafficStats.getTotalRxBytes();
        long mStarTX = TrafficStats.getTotalTxBytes();

        long startTime = System.currentTimeMillis();

            while (initialLevel == level) {
                mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
                level = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                      level=level-1;
                       try {
                           Thread.sleep(5000);
                       }
                       catch (InterruptedException ex){

                       }
            }
            //After Check

            Memory memoryCheckAfter = new Memory();
            memoryCheckAfter.getMemoryUsage();
            Double memoryUsage = (memoryCheckAfter.getMemoryUsage() - memoryCheckBefore);

            long rxBytes = TrafficStats.getTotalRxBytes() - mStarRX;
            long txBytes = TrafficStats.getTotalTxBytes() - mStarTX;

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);

            sendLevel(level,duration);


            Float CPUAfter=cpu.readUsage();
            Float CPUUsage=(CPUAfter-CPUBefore);
            Log.d("LEVEL CPU",CPUUsage.toString());

            Log.d("LEVEL TIME", Long.toString(duration));
            Log.d("LEVEL MEMORY", memoryUsage.toString());
            Log.d("LEVEL TX", Long.toString(txBytes));
            Log.d("LEVEL RX", Long.toString(rxBytes));

                   /*final SocketIo socketIo=new SocketIo();
                   socketIo.connect();
                   socketIo.sendUsage(duration,expression);
                   socketIo.getSocket();*/

            //After drop of one in battery percentage
            initialLevel = level;
            memoryCheckBefore = memoryCheck.getMemoryUsage();
            mStarRX = TrafficStats.getTotalRxBytes();
            mStarTX = TrafficStats.getTotalTxBytes();
        }
    }
    public void sendLevel(int level,long duration) {
        /// Send Registration details
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("monitor-data-received");
        // You can also include some extra data.
        intent.putExtra("level", level);
        intent.putExtra("duration", duration);

        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intent);
    }
}
