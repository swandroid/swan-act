package interdroid.swancore;

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
    public HashMap<String,String> parameters=new HashMap<String,String>();

    public void expressionParse(String expression) {

        String[]then=expression.split("THEN");
        Log.d("Then-Check",then[0]);
        this.expression=then[0];
        String[]zero=then[1].split("\\|\\||&&");
        if(zero.length<2) {
            String[] one = then[1].split("\\@");
            this.deviceId = one[0];
            String[] two = one[1].split("\\:");
            this.valuePath = two[0];
            Log.d("valuePath",valuePath);
            String[] three = two[1].split("\\?");
            if(three.length<2){
                this.option=two[1];
            }
            else {
                this.option = three[0];
                String[] four = three[1].split("\\&");
                for (int l = 0; l < four.length; l++) {
                    String params[] = four[l].split("\\=");
                    String param = params[0];
                    String paramValue = params[1];
                    this.parameters.put(param, paramValue);
                }
            }
        }
    }
}
