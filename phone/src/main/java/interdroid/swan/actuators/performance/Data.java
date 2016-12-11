package interdroid.swan.actuators.performance;

import android.net.TrafficStats;

/**
 * Created by rm on 28/10/2016.
 */

public class Data {
    private long mStartRX = 0;
    private long mStartTX = 0;

    public void getDataUsage(){
        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();
        long rxBytes = TrafficStats.getTotalRxBytes()- mStartRX;
        long txBytes = TrafficStats.getTotalTxBytes()- mStartTX;
    }
}
