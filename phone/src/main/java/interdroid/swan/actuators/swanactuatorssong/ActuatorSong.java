package interdroid.swan.actuators.swanactuatorssong;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by rm on 06/10/2016.
 */

public class ActuatorSong {
    public String expression="";
    public String deviceId="";
    public String valuePath="";
    public String option="";
    public String parameters="";
    public void expressionParse(String expression) {

        String[]then=expression.split("THEN",2);
        Log.d("Then-Check",then[0]);
        this.expression=then[0];


        String[]zero=then[1].split("\\|\\||&&");
        if(zero.length<2) {
            String[] one = then[1].split("\\@",2);
            this.deviceId = one[0];
            String[] two = one[1].split("\\:",2);
            this.valuePath = two[0];
            Log.d("valuePath",valuePath);
            String[] three = two[1].split("\\?",2);
            if(three.length<2){
                this.option=two[1];
                this.parameters="none";
            }
            else {
                this.option = three[0];
                this.parameters = three[1];
            }
        }

        /*else if(zero.length>1){
                this.swanExpression=then[0];
                String[]before=then[1].split("\\|\\||&&");
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
        }*/
    }
}
