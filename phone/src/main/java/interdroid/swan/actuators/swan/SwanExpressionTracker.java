package interdroid.swan.actuators.swan;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by rm on 22/09/16.
 */
public class SwanExpressionTracker {
    public HashMap<Integer, Context> expressions = new HashMap<>();

    public void addExpression(int expressionId, Context context)
    {
        expressions.put(expressionId,context);
    }
    public Context getExpressionData(int expressionId){
        return expressions.get(expressionId);
    }

}
