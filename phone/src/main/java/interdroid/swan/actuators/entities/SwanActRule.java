package interdroid.swan.actuators.entities;

import interdroid.swan.actuators.database.DB;

import org.droidparts.annotation.sql.Column;
import org.droidparts.annotation.sql.Table;
import org.droidparts.model.Entity;

import java.util.ArrayList;

/**
 * Created by rm on 24/08/16.
 */
@Table
public class SwanActRule extends Entity {

    @Column(name = DB.Column.SWANEXP, eager = true)
    private SwanExp mswanExp;

    @Column(name = DB.Column.TRIGGER)
    private String mTrigger;

    @Column(name = DB.Column.ACTIONS, eager = true)
    private ArrayList<Action> mActions;

    public void setPuck(SwanExp swanExp) { this.mswanExp = swanExp; }

    public SwanExp getPuck() { return mswanExp; }

    public String getTrigger() {
        return mTrigger;
    }

    public void setTrigger(String trigger) {
        this.mTrigger = trigger;
    }

    public ArrayList<Action> getActions() {
        return mActions;
    }

    public void addAction(Action action) {
        if (mActions == null) {
            mActions = new ArrayList<>();
        }
        mActions.add(action);
    }
}
