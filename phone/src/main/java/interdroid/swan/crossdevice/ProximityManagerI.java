package interdroid.swan.crossdevice;

import interdroid.swan.crossdevice.wifidirect.WDSwanDevice;

/**
 * Created by vladimir on 3/10/16.
 */
public interface ProximityManagerI {

    int getPeerCount();

    WDSwanDevice getPeerAt(int position);

    boolean hasPeer(String username);

    void init();

    void clean();

    void registerService();

    void discoverPeers();

    void registerExpression(String id, String expression, String resolvedLocation);

    void unregisterExpression(String id, String expression, String resolvedLocation);

    void send(String toPeerName, String expressionId, String action, String data);
}
