package interdroid.swan.actuators.entities;

import org.droidparts.annotation.sql.Column;

/**
 * Created by rm on 28/08/16.
 */
public class Connection {
    @Column
    private String connectionAdaptor;

    @Column
    private int port;
}
