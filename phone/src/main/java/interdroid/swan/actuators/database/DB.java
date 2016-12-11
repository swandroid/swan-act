package interdroid.swan.actuators.database;

import java.util.HashMap;

/**
 * Created by rm on 24/08/16.
 */
public class DB {

    public interface Column extends org.droidparts.annotation.sql.Column{
        String EXP_NAME="expressionname";
        String EXP_DEVICE_ID="expressiodeviceid";
        String EXP_ID="expressionid";
        String SENDER_ID="senderid";
        String RECEIVER_ID="receiverid";
        String SWANEXP = "swanexp";
        String TRIGGER = "trigger";
        String ACTIONS = "actions";
        String PARAMETERS="parameters";
    }
}
