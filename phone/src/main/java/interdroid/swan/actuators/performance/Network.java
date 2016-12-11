package interdroid.swan.actuators.performance;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.util.Log;

import org.droidparts.annotation.inject.InjectDependency;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by rm on 27/10/2016.
 */

public class Network {

    @InjectDependency
    Context mContext;
    int UID = android.os.Process.myUid();
    long rxBytes= getUidRxBytes(UID);
    long txBytes= getUidTxBytes(UID);
    /**
     * Read UID Rx Bytes
     *
     * @param uid
     * @return rxBytes
     */
    public Long getUidRxBytes(int uid) {
        BufferedReader reader;
        Long rxBytes = 0L;
        try {
            reader = new BufferedReader(new FileReader("/proc/uid_stat/" + uid
                    + "/tcp_rcv"));
            rxBytes = Long.parseLong(reader.readLine());
            reader.close();
        }
        catch (FileNotFoundException e) {
            rxBytes = TrafficStats.getUidRxBytes(uid);
            //e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return rxBytes;
    }

    /**
     * Read UID Tx Bytes
     *
     * @param uid
     * @return txBytes
     */
    public Long getUidTxBytes(int uid) {
        BufferedReader reader;
        Long txBytes = 0L;
        try {
            reader = new BufferedReader(new FileReader("/proc/uid_stat/" + uid
                    + "/tcp_snd"));
            txBytes = Long.parseLong(reader.readLine());
            reader.close();
        }
        catch (FileNotFoundException e) {
            txBytes = TrafficStats.getUidTxBytes(uid);
            //e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return txBytes;
    }


}
