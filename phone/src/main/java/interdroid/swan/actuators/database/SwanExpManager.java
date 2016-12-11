package interdroid.swan.actuators.database;

import android.content.Context;

import interdroid.swan.actuators.entities.SwanExp;

import org.droidparts.persist.sql.EntityManager;
import org.droidparts.persist.sql.stmt.Is;
import org.droidparts.persist.sql.stmt.Select;
import org.droidparts.persist.sql.stmt.Where;

import java.util.ArrayList;

/**
 * Created by rm on 24/08/16.
 */
public class SwanExpManager extends EntityManager<SwanExp> {

    public SwanExpManager(Context ctx) {
        super(SwanExp.class, ctx);
    }


    public Select<SwanExp> find(String expName, String expDevId, String expId) {
        Where query =
                new Where(DB.Column.EXP_NAME, Is.EQUAL, expName)
                        .and(DB.Column.EXP_DEVICE_ID, Is.EQUAL, expDevId)
                        .and(DB.Column.EXP_ID, Is.EQUAL, expId);
        return select().where(query);
    }


    public SwanExp read(String expName, String expDevId, String expId) {
        return readFirst(find(expName, expDevId, expId));
    }

    public ArrayList<SwanExp> getAll() {
        return readAll(select());
    }

}