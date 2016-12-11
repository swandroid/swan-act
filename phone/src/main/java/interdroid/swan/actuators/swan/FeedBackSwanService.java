package interdroid.swan.actuators.swan;

import android.content.Context;
import android.util.Log;

import org.droidparts.annotation.inject.InjectDependency;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import interdroid.swan.actuators.ActuatorManager;
import interdroid.swancore.swanmain.ExpressionManager;

/**
 * Created by rm on 19/10/2016.
 */



public class FeedBackSwanService {
   Context mContext;
    public void changeParameters(Context context,String expression,String window){
        String id;
        id=ActuatorManager.runningExpressions.get(expression);
        this.mContext=context;
        ExpressionManager.unregisterExpression(this.mContext,id);
        String[] expressionParams=expression.split(",");
        String[] one=expressionParams[0].split("\\{");
        String[] two=expressionParams[1].split("\\}");
        String newExpression=one[0]+"{"+window+"}"+two[1];
        SwanSensor sv=new SwanSensor();
        sv.registerSWANSensor(mContext,newExpression,id);
    }
}
