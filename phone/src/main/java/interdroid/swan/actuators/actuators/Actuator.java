package interdroid.swan.actuators.actuators;

import android.content.Context;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Actuator {

    @InjectDependency
    Context mContext;

    public static HashMap<Integer, Actuator> mActuators;

    static {
        mActuators = createActuatorList();
        Log.d("MainActuator","Main");
    }


    public abstract int getId();

    public abstract void setId(int id);

    public abstract String[] getOptions();

    public abstract String getActuator();


    public static ArrayList<Actuator> getActuators() {
        return new ArrayList<>(mActuators.values());
    }


    public static Actuator getActuatorForId(int id) {
        return mActuators.get(id);
    }

    public Actuator() {
        Injector.inject(Injector.getApplicationContext(), this);
    }


    abstract void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException;

    //Converted to Object from below
    public void actuate(String arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {
        actuate(new JSONObject(arguments), expressionId,completeExpression,remote,sendingDevice,receivingDevice,parameters,trigger);
    }

    public static void actuate(int actuatorId, String arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {
        mActuators.get(actuatorId).actuate(arguments, expressionId,completeExpression,remote,sendingDevice,receivingDevice,parameters,trigger);
    }


    private static HashMap<Integer, Actuator> createActuatorList() {
        HashMap<Integer, Actuator> actuators = new HashMap<>();

        //Initialize your actuator here

        Actuator actuator = new VibratorActuator();
        actuators.put(0, actuator);
        actuator.setId(0);

        actuator=new ExpressionActuator();
        actuators.put(1,actuator);
        actuator.setId(1);

        actuator=new ThingsSpeakActuator();
        actuators.put(2,actuator);
        actuator.setId(2);

        actuator=new FeedBackActuator();
        actuators.put(3,actuator);
        actuator.setId(3);

        //Special
        actuator=new VirtualSensorActuator();
        actuators.put(200,actuator);
        actuator.setId(200);


        //}
       /* actuator = new RingerActuator();
        if(actuator.actuatorExists()==TRUE) {
            actuators.put(actuator.getId(), actuator);
        }
        actuator = new CalendarActuator();
        actuators.put(actuator.getId(), actuator);
        actuator = new ThingsSpeakActuator();
        actuators.put(actuator.getId(), actuator);
        actuator = new MusicVolumeActuator();
        actuators.put(actuator.getId(), actuator);
        actuator=new VibratorActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new SocketActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new SocketActuator2();
        actuators.put(actuator.getId(),actuator);
        actuator=new ExpressionActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new FeedBackActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new ToggleActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new TaskActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new VirtualSensorActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new CameraActuator();
        actuators.put(actuator.getId(),actuator);
        actuator=new ApplicationActuator();
        actuators.put(actuator.getId(),actuator);*/
        return actuators;
    }

}
