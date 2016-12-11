package interdroid.swan.actuators.database;

/**
 * Created by rm on 24/08/16.
 */
import android.content.Context;

import interdroid.swan.actuators.entities.SwanActRule;
import interdroid.swan.actuators.entities.SwanExp;

import org.droidparts.persist.sql.EntityManager;
import org.droidparts.persist.sql.stmt.Is;
import org.droidparts.persist.sql.stmt.Select;

import java.util.ArrayList;
import java.util.List;


public class RuleManager extends EntityManager<SwanActRule> {

    public RuleManager(Context ctx) {
        super(SwanActRule.class, ctx);
    }

    public ArrayList<SwanActRule> getRulesForTrigger(String trigger) {
        return readAll(select().where(DB.Column.TRIGGER, Is.EQUAL, trigger));
    }

    public List<SwanActRule> getRulesForTriggers(String... triggers) {
        List<SwanActRule> swanActRules = new ArrayList<>();
        for (String trigger : triggers) {
            swanActRules.addAll(getRulesForTrigger(trigger));
        }
        return swanActRules;
    }

    public Select<SwanActRule> getRulesForPuck(SwanExp swanExp) {
        if (swanExp == null) {
            return null;
        } else {
            return select().where(DB.Column.SWANEXP, Is.EQUAL, swanExp.id);
        }
    }

    public ArrayList<SwanActRule> getRulesForPuckAndTrigger(SwanExp swanExp, String triggerIdentifier) {
        if (swanExp == null || triggerIdentifier == null) {
            return new ArrayList<>();
        } else {
            return readAll(select()
                    .where(DB.Column.SWANEXP, Is.EQUAL, swanExp.id)
                    .where(DB.Column.TRIGGER, Is.EQUAL, triggerIdentifier));
        }
    }

    public void createOrExtendExisting(SwanActRule swanActRule) {
        List<SwanActRule> swanActRules = getRulesForPuckAndTrigger(swanActRule.getPuck(), swanActRule.getTrigger());
        if (swanActRules.size() > 0) {
            SwanActRule toExtend = swanActRules.get(0);
            fillForeignKeys(toExtend);
            toExtend.getActions().addAll(swanActRule.getActions());
            update(toExtend);
        } else {
            super.create(swanActRule);
        }
    }
}