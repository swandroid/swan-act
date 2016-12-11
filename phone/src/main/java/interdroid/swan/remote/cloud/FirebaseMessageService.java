package interdroid.swan.remote.cloud;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.droidparts.bus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interdroid.swan.actuators.actuation.Actuate;
import interdroid.swancore.crossdevice.Converter;
import interdroid.swancore.swansong.Result;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.TriState;

/**
 * Created by Roshan Bharath Das on 28/06/16.
 */
public class FirebaseMessageService extends FirebaseMessagingService{
    //CloudManager cloudManager = CloudManager.getCreatedInstance();
    //ROSHAN'S WORK
    //static int noOfTimes = 0;
    private static final String TAG = "FirebaseMessageService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //// Gets the body data from server ///heroku.../expression
        //String received=remoteMessage.getNotification().getBody().toString();
        Map<String, String> data = remoteMessage.getData();
        String actuationType = data.get("actuationtype");
        String completeexpression = data.get("completeexpression");
        String dataa = data.get("data");
        String senderid = data.get("senderid");
        String receiverid = data.get("receiverid");
        sendRemoteMessage(actuationType,completeexpression,dataa,senderid,receiverid);


        //ROSHAN'S WORK
      /*  Log.d(TAG, "From: " + remoteMessage.getFrom());

        Log.d(TAG, "Message body: "+ remoteMessage.getData().get("field"));


        String message = remoteMessage.getData().get("field");

        try {
            JSONObject jsonResult = new JSONObject(message);

            Result result = null;
            if(jsonResult.has("id") && jsonResult.has("action") && jsonResult.has("data") && jsonResult.has("timestamp")) {


                if(jsonResult.getString("action").contentEquals("register-value")) {

                    noOfTimes++;

                    TimestampedValue[]  timestampedValues = new TimestampedValue[1];

                    timestampedValues[0] = new TimestampedValue(jsonResult.get("data"),(long) jsonResult.get("timestamp"));

                    result = new Result(timestampedValues,(long) jsonResult.get("timestamp"));


                }
                else if(jsonResult.getString("action").contentEquals("register-tristate")){

                    noOfTimes++;
                    TriState triState;
                    if(jsonResult.getString("data").contentEquals("true")){
                        triState= TriState.TRUE;
                    }
                    else if(jsonResult.getString("data").contentEquals("false")){
                        triState=TriState.FALSE;
                    }
                    else{
                        triState=TriState.UNDEFINED;
                    }
                    result = new Result((long) jsonResult.get("timestamp"),triState);
                    result.setDeferUntilGuaranteed(false);
                }


                if(result!=null) {
                    cloudManager.sendResult(jsonResult.getString("id"), jsonResult.getString("action"), Converter.objectToString(result));
                }
            }
            else{
                noOfTimes= noOfTimes+2;
                Log.e("Roshan","SWAN Cloud Communication "+noOfTimes);
            }
            //TODO: Handle unregister request
          //  else if(jsonResult.has("id") && jsonResult.has("action")){
           //     cloudManager.sendResult(jsonResult.getString("id"), jsonResult.getString("action"), Converter.objectToString(result));

          //


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
    private void sendRemoteMessage(String actuationType, String completeexpression, String dataa, String senderid, String receiverid) {
        Log.d("remoteDataReceiver", "Broadcasting message");
        Intent intent = new Intent("remote-data-received");
        intent.putExtra("actuationtype", actuationType);
        intent.putExtra("completeexpression",completeexpression);
        intent.putExtra("data",dataa);
        intent.putExtra("senderid",senderid);
        intent.putExtra("receiverid",receiverid);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
