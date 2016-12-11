package interdroid.swan.crossdevice.wifidirect;

import android.net.wifi.p2p.WifiP2pDevice;

import java.util.Collection;

/**
 * Created by vladimir on 9/8/15.
 */
public interface WDPeerToPeerI {

    void updatePeers(Collection<WifiP2pDevice> deviceList);

    void processWDUpdate(String update);
}
