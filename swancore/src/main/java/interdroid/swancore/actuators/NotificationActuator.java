package interdroid.swancore.actuators;

/**
 * Created by rm on 14/10/2016.
 */

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import interdroid.swancore.R;
import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SwanException;
import interdroid.swancore.swanmain.TriStateExpressionListener;
import interdroid.swancore.swanmain.ValueExpressionListener;
import interdroid.swancore.swansong.ExpressionFactory;
import interdroid.swancore.swansong.ExpressionParseException;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.TriState;
import interdroid.swancore.swansong.TriStateExpression;
import interdroid.swancore.swansong.ValueExpression;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationActuator  {

    public Context context;

    public static final String ACTUATOR = "notification";
    public static final String[] OPTIONS = new String[] { "send", "notsend", "ok" };


    String currentValue;
    int count=0;



    public NotificationActuator(Context context){this.context=context;}

    public String describeActuator() {
        return "notification";
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

    String value="0";

    public void actuate(JSONObject arguments, String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice) throws JSONException {
        //ExpressionManager expressionManager=new ExpressionManager();


        Random rand=new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomId = rand.nextInt((2000 - 0) + 1) + 0;
        expressionId="self@light:lux{ANY,10ms}<10.0";

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
            ExpressionManager.registerTriStateExpression(context, String.valueOf(randomId),
                    (TriStateExpression) ExpressionFactory.parse(expressionId),
                    new TriStateExpressionListener() {

                        @Override
                        public void onNewState(String arg0, long arg1, TriState arg2) {
                            Log.d("ARGUMENT",arg2.toString());
                            if (arg2 == TriState.TRUE) {
                                Log.d("TRI","TRUE");
                                setValue("TRUE");
                            }
                        }
                    });
        }catch (SwanException e) {
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
            Log.d("Building","Building Notification");
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("Expression Evaulated")
                            .setContentText("Received True!");
            mBuilder.build();
            int mNotificationId = 001;
            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());

    }
    public String getValue(){
        return this.currentValue;
    }

}

