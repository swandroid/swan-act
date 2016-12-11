package interdroid.swan.crossdevice;

import java.util.HashMap;

/**
 * Created by vladimir on 7/15/16.
 */
public interface CrossdeviceConnectionI {
    void send(HashMap<String, String> dataMap) throws Exception;

    void disconnect();

    boolean isConnected();
}
