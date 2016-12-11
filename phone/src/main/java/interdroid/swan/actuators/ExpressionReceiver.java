package interdroid.swan.actuators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import org.droidparts.annotation.inject.InjectDependency;

/**
 * Created by rm on 27/11/2016.
 */

public class ExpressionReceiver extends BroadcastReceiver {
    @InjectDependency
    Context mContext;

    /**
     * Received from Android EndPoint
     * @param context
     * @param intent
     */

    @Override
    public void onReceive(Context context, Intent intent) {
            String requestType = intent.getStringExtra("type");
            String id = intent.getStringExtra("id");
            String name = intent.getStringExtra("expression");
            Toast.makeText(context, name, Toast.LENGTH_LONG).show();
            sendRemoteMessage(requestType, name, id, "null", "null");
    }

    /**
     *
     * @param actuationType Register and unregister
     * @param completeexpression
     * @param dataa
     * @param senderid Not Required to be specified
     * @param receiverid Not Required to be specified
     */

    private void sendRemoteMessage(String actuationType, String completeexpression, String dataa, String senderid, String receiverid) {
        Log.d("remoteDataReceiver", "Broadcasting message");
        Intent intent = new Intent("remote-data-received");
        intent.putExtra("actuationtype", actuationType);
        intent.putExtra("completeexpression",completeexpression);
        intent.putExtra("data",dataa);
        intent.putExtra("senderid",senderid);
        intent.putExtra("receiverid",receiverid);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }
}
