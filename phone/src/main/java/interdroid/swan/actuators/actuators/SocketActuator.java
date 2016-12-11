package interdroid.swan.actuators.actuators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.RequestQueue;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import interdroid.swan.actuators.SocketHelper;

/**
 * Created by rm on 15/10/2016.
 */
public class SocketActuator extends Actuator {
    public static final String[] OPTIONS = new String[]{"post","put"};
    public static final String ACTUATOR = "socket";

    //@InjectDependency
    RequestQueue mRequestQueue;

    public static int id;
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int idToSet){
        id=idToSet;
    }

    @Override
    public String[] getOptions() {
        return OPTIONS;
    }

    @Override
    public String getActuator() {
        return ACTUATOR;
    }

    Random number=new Random();

    Integer count=0;
    SocketHelper socket;

    @Override
    public synchronized void actuate(JSONObject arguments, final String expressionId, String completeExpression, String remote, String sendingDevice, String receivingDevice,String parameters,String trigger) throws JSONException {

        int amount = (int) arguments.get(ACTUATOR);
        if (amount == 0) {
            socket=new SocketHelper("https://swan-exp-eval.herokuapp.com");
            socket.connect();

            LocalBroadcastManager.getInstance(mContext).registerReceiver(mDataReceived,
                    new IntentFilter(trigger));
        }
    }

    private Emitter.Listener onReply = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = (String)args[0];
        }
    };

    private BroadcastReceiver mDataReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String data = intent.getStringExtra("data");
            socket.attemptSend("new message",data);
        }
    };

}

