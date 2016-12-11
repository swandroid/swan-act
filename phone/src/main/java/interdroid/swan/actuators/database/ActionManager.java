package interdroid.swan.actuators.database;

import android.content.Context;

import interdroid.swan.actuators.entities.Action;

import org.droidparts.persist.sql.EntityManager;

/**
 * Created by rm on 24/08/16.
 */
public class ActionManager extends EntityManager<Action> {
    public ActionManager(Context ctx) {
        super(Action.class, ctx);
    }
}
