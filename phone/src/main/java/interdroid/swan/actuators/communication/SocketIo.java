package interdroid.swan.actuators.communication;

import android.text.TextUtils;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.On;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rm on 30/09/2016.
 */
public class SocketIo {
    private Random r = new Random();
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket( "https://swan-exp-eval.herokuapp.com");
        } catch (URISyntaxException e) {}
    }

    public void connect() {
        mSocket.connect();
    }

    public void getSocket(){
        mSocket.on("Reply",onReply);
    }

    public void attemptSend(Integer count) {
        String message = "Socket Test Value:";
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("new message", message+count.toString());
    }

    public void initSpam(){
        //for(int i=0;i<10000;i++) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for(int i=0;i<1000;i++) {
                    /*Object b = new Object();
                    b.putInt("INT", r.nextInt());
                    b.putLong("LONG", r.nextLong());
                    b.putBoolean("BOOL", r.nextBoolean());
                    b.putFloat("FLOAT", r.nextFloat());
                    b.putDouble("DOUBLE", r.nextDouble());
                    b.putString("STRING", String.valueOf(r.nextInt()));
                    Message msg = Message.obtain();*/
                    //msg.setData(b);
                     mSocket.emit("new message","From Client");
                }
            }

        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task,1,300000);
    }

    public void sendUsage(long duration,String expression) {
        String message = "Battery Test:";
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("new message", message+expression+Long.toString(duration));
    }

    private Emitter.Listener onReply = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = (String)args[0];
                //Log.d("receivedserver",data);
        }
    };
}
