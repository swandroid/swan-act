package interdroid.swan.actuators.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import interdroid.swan.actuators.actuation.Actuate;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.droidparts.bus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rm on 08/07/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        /*String expressn="";
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        String receivedFromRemote = remoteMessage.getNotification().getBody();
        try {
            JSONObject myObject = new JSONObject(receivedFromRemote);
            expressn = myObject.getString("exp");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //MainActivity.express=remoteMessage.getNotification().getBody().toString();
        //MainActivity.express=expressn;
        //Calling method to generate notification
        //sendNotification(remoteMessage.getNotification().getBody());
        //MainActivity.express=remoteMessage.getNotification().getBody().toString();

        //EventBus.postEvent(Actuate.TRIGGER_ENTER_ZONE);

        String data="";
        String expression="";
        String actuator="";
        String userid="";
        String options="";
        String describe="";
        String actuationType="";


        //// Gets the body data from server ///heroku.../expression
        String received=remoteMessage.getNotification().getBody().toString();
        //String receivedFromRemote = remoteMessage.getNotification().getBody().toString();
        try {
            JSONObject myObject = new JSONObject(received);
            actuationType=myObject.getString("actuationtype");
            data = myObject.getString("expr");
            expression=myObject.getString("exp");
            userid=myObject.getString("useid");
            actuator=myObject.getString("actuator");
            options=myObject.getString("options");
            describe=myObject.getString("describe");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(received.contentEquals("newexp")){
            EventBus.postEvent(Actuate.NEW_EXPRESSION_DETECTED);
        }
        else if (received.contentEquals("upcount")){
            EventBus.postEvent(Actuate.TRIGGER_UPCOUNT);
        }
        else if (received.contentEquals("countdown")){
            EventBus.postEvent(Actuate.TRIGGER_DOWNCOUNT);
        }
        else if (received.contentEquals("registerexp")){
            EventBus.postEvent(Actuate.REGISTER_EXPRESSION);
        }
        else if (received.contentEquals("authenticate")){
            EventBus.postEvent(Actuate.AUTHENTICATION);
        }
        else if (received.contentEquals("remote")){
            //sendRemoteMessage(743,"WebActuator","put,post,delete","WebActuator");
        }
        else if (data.contentEquals("config")){
            EventBus.postEvent(Actuate.LOAD_CONIFG);
        }
        else if(actuationType.contentEquals("localact")){
            try {
                JSONObject mylocalObject = new JSONObject(received);
                actuationType=mylocalObject.getString("actuationtype");
                String completeexpression = mylocalObject.getString("completeexpression");
                String dataa=mylocalObject.getString("data");
                String senderid=mylocalObject.getString("senderid");
                String receiverid=mylocalObject.getString("receiverid");
                sendRemoteMessage(actuationType,completeexpression,dataa,senderid,receiverid);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(actuationType.contentEquals("remoteswanlocalact")){
            try {
                JSONObject mylocalObject = new JSONObject(received);
                actuationType=mylocalObject.getString("actuationtype");
                String completeexpression = mylocalObject.getString("completeexpression");
                String dataa=mylocalObject.getString("data");
                String senderid=mylocalObject.getString("senderid");
                String receiverid=mylocalObject.getString("receiverid");
                sendRemoteMessage(actuationType,completeexpression,dataa,senderid,receiverid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(actuationType.contentEquals("fullyremote")){
            try {
                JSONObject mylocalObject = new JSONObject(received);
                actuationType=mylocalObject.getString("actuationtype");
                String completeexpression = mylocalObject.getString("completeexpression");
                String dataa=mylocalObject.getString("data");
                String senderid=mylocalObject.getString("senderid");
                String receiverid=mylocalObject.getString("receiverid");
                sendRemoteMessage(actuationType,completeexpression,dataa,senderid,receiverid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if(data.contentEquals("remote")){
            //sendRemoteMessage(741,actuator,options,describe);
        }



        //Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //sendNotification("Expression Received:"+received);
    }

    /*private void sendRemoteMessage(int anInt, String actuator, String options, String describeActuator) {
        Log.d("remoteDataReceiver", "Broadcasting message");
        Intent intent = new Intent("remote-data-received");
        // You can also include some extra data.
        intent.putExtra("id", anInt);
        intent.putExtra("actuator",actuator);
        intent.putExtra("options",options);
        intent.putExtra("describe",describeActuator);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }*/

    private void sendRemoteMessage(String actuationType, String completeexpression, String dataa, String senderid, String receiverid) {
        Log.d("remoteDataReceiver", "Broadcasting message");
        Intent intent = new Intent("remote-data-received");
        // You can also include some extra data.
        intent.putExtra("actuationtype", actuationType);
        intent.putExtra("completeexpression",completeexpression);
        intent.putExtra("data",dataa);
        intent.putExtra("senderid",senderid);
        intent.putExtra("receiverid",receiverid);


        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
    
}

