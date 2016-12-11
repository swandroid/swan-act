package interdroid.swan.actuators.actuation;

import interdroid.swan.actuators.actuators.Actuator;
import interdroid.swan.actuators.database.RuleManager;
import interdroid.swan.actuators.entities.Action;
import interdroid.swan.actuators.entities.SwanActRule;
import interdroid.swan.actuators.entities.SwanExp;

import org.droidparts.Injector;
import org.droidparts.util.L;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by rm on 24/08/16.
 */
public abstract class Actuate {

    public static final String TRIGGER_REMOVE_RULE = "trigger_remove_rule";
    public static final String TRIGGER_ADD_RULE_FOR_EXISTING_EXPRESSION = "trigger_add_rule_for_existing_puck";
    public static final String NEW_EXPRESSION_DETECTED = "new_expression_detected";
    public static final String TRIGGER_UPCOUNT = "UPCOUNT";
    public static final String TRIGGER_DOWNCOUNT = "DOWNCOUNT";
    public static final String REGISTER_EXPRESSION="REGISTEREXP";
    public static final String AUTHENTICATION="AUTHENTICATE";
    public static final String LOAD_CONIFG="LOAD_CONFIG";

    public static void trigger(SwanExp swanExp, String trigger) {
        L.i("Triggering event for rule " + swanExp + " and trigger " + trigger);

        ArrayList<SwanActRule> swanActRules = Injector.getDependency(Injector.getApplicationContext(),
                RuleManager.class).getRulesForPuckAndTrigger(swanExp, trigger);
        L.i("With matching swanActRules " + swanActRules);
        String expressionId=swanExp.getName();
        String checkRemote=swanExp.getExpDevId();

        String completeExpression=swanExp.getExpId();
        ////Experimental
        String expToRun=swanExp.getName();

        String sendingDevice=swanExp.getSenderId();
        String receivingDevice=swanExp.getReceiverId();
        String parameters=swanExp.getParameters();
        ///
        for(SwanActRule swanActRule : swanActRules) {
            for (Action action : swanActRule.getActions()) {
                try {
                    Actuator.actuate(action.getActuatorId(), action.getArguments(), expressionId, completeExpression,checkRemote,sendingDevice,receivingDevice,parameters,trigger);
                } catch (JSONException e) {
                    L.e(e);
                }
            }
        }
    }
}
