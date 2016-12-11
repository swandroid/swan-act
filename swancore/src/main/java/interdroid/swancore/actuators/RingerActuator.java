package interdroid.swancore.actuators;

import android.content.Context;
import android.util.Log;
import android.util.LongSparseArray;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SwanException;
import interdroid.swancore.swanmain.ValueExpressionListener;
import interdroid.swancore.swansong.ExpressionFactory;
import interdroid.swancore.swansong.ExpressionParseException;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.ValueExpression;


public class RingerActuator  {

    public Context context;

    public static final String ACTUATOR = "ringer";
    public static final String[] OPTIONS = new String[] { "silent", "vibrateonly", "volumeon" };


    String currentValue;
    int count=0;



    //public RingerActuator(Context context){this.context=context;}

    public String describeActuator() {
        return "Phone Volume Actuator";
    }

    public String describeArguments(JSONObject arguments) {
        try {
            return "Sets your phone to " + OPTIONS[arguments.getInt(ACTUATOR)];
        } catch (JSONException e) {
            e.printStackTrace();
            return "Invalid arguments for actuator";
        }
    }

    public int getId() {
        return 2;
    }

    public String[] getOptions() {
        return OPTIONS;
    }

    public String getActuator() {
        return ACTUATOR;
    }

    public RingerActuator(Context context){this.context=context;}

    String value="0";

    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice) throws JSONException {
        //ExpressionManager expressionManager=new ExpressionManager();

        Random rand=new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomId = rand.nextInt((2000 - 0) + 1) + 0;
        expressionId="self@light:lux{ANY,10ms}";

        swanService(randomId,expressionId);


        /*while(1>0){
            postDeviceInfo(getValue());
            try{// Due to limitation of THingsSpeak update should be only after 15 seconds
                Thread.sleep(16000);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }*/
    }

    public void swanService(int randomId,String expressionId){
        try {
            ExpressionManager.registerValueExpression(context, String.valueOf(randomId),
                    (ValueExpression) ExpressionFactory.parse(expressionId),
                    new ValueExpressionListener() {

                        @Override
                        public void onNewValues(String id,
                                                TimestampedValue[] arg1) {
                            if (arg1 != null && arg1.length > 0) {
                                //Double result=(Double)arg1[0].getValue();
                                String value=arg1[0].getValue().toString();
                                Long timestamp = arg1[0].getTimestamp();
                                //mCircularQueue.expressionQueue(value);
                                Log.d("SENSOR VALUES", value.toString());
                                //Log.d("SENSOR TIME", timestamp.toString());

                                setValue(value);

                            }
                        }

                    });
        } catch (SwanException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExpressionParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void setValue(String value){
        this.currentValue=value;
        //SocketIo socketIo=new SocketIo();
        //socketIo.connect();
        //socketIo.attemptSend();
        count++;
        if(count==3){
          /*  Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(5000);*/
        }

    }
    public String getValue(){
        return this.currentValue;
    }

}
