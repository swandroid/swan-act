package interdroid.swancore;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import interdroid.swancore.actuators.NotificationActuator;
import interdroid.swancore.actuators.RingerActuator;


/**
 * Created by rm on 12/10/2016.
 */

public class RegisterActuators {

    Context context;
    NotificationActuator na1;
    RingerActuator ra1;

    public void loadActuators(Context context){
        this.context=context;
        na1=new NotificationActuator(this.context);
        ra1=new RingerActuator(this.context);
    }

    public void loadConfig(String expression, String parameters){

        JSONObject login = new JSONObject();
        try {
            login.put("key", "value");
        } catch (JSONException e) { }


        ActuatorSong actuatorSong=new ActuatorSong();
        actuatorSong.expressionParse(expression);
        String option=actuatorSong.option;

        if(option.contentEquals("notification")){
            try {
                na1.actuate(login, "dfdfd", "sdsd", "sdsdsd", "SDSdsds", "SDsd");

            }
            catch (JSONException e) { }
        }
        else if(option.contentEquals("ringer")){
            try {
                ra1.actuate(login, "dfdfd", "sdsd", "sdsdsd", "SDSdsds", "SDsd");
            }
            catch (JSONException e) { }

        }

    }

}
