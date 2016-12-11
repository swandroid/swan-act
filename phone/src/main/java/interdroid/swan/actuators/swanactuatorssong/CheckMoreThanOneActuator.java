package interdroid.swan.actuators.swanactuatorssong;

import android.util.Log;

/**
 * Created by rm on 15/10/2016.
 */

public class CheckMoreThanOneActuator {

    String [] dividedExpressions;
    String swanExpression;

    public String[] check(String expression){
        String[]then=expression.split("THEN");
        Log.d("Then-Check",then[0]);
        this.swanExpression=then[0];
        String[]zero=then[1].split("\\|\\||&&");
        if(zero.length>1) {
            dividedExpressions=new String[zero.length];
            for(int i=0;i<zero.length;i++){
                dividedExpressions[i]=swanExpression+"THEN"+zero[i];
            }
        }
        if(zero.length<2){
            dividedExpressions=new String[1];
            dividedExpressions[0]=swanExpression+"THEN"+then[1];
        }
        return dividedExpressions;
    }
}
