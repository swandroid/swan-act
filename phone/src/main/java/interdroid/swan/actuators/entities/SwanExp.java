package interdroid.swan.actuators.entities;

import interdroid.swan.actuators.database.DB;

import org.droidparts.annotation.sql.Column;
import org.droidparts.model.Entity;

/**
 * Created by rm on 24/08/16.
 */
public class SwanExp extends Entity {

    @Column(name = DB.Column.EXP_NAME)
    private String expName;

    @Column(name = DB.Column.EXP_DEVICE_ID)
    private String expDevId;

    @Column(name = DB.Column.EXP_ID)
    private String expId;

    @Column(name = DB.Column.SENDER_ID)
    private String senderId;

    @Column(name = DB.Column.RECEIVER_ID)
    private String receiverId;

    @Column(name = DB.Column.PARAMETERS)
    private String parameters;

    @Column(name = DB.Column.TRIGGER)
    private String trigger;

    public void setName(String name) {
        this.expName = name;
    }

    public String getName() {
        return this.expName;
    }

    public String getExpDevId() {
        return expDevId;
    }

    public String getExpId() {
        return expId;
    }

    public String getSenderId(){return senderId;}

    public String getReceiverId() {return receiverId;}

    public String getParameters(){return parameters;}

    public String getTrigger(){return trigger;}

    public SwanExp() {}

    public SwanExp(String expName, String expDevId, String expId, String senderId, String receiverId,String parameters,String trigger ){
        this.expName = expName;
        this.expDevId = expDevId;
        this.expId = expId;
        this.senderId=senderId;
        this.receiverId=receiverId;
        this.parameters=parameters;
        this.trigger=trigger;
    }

}
