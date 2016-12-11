package interdroid.swan.actuators;
import android.text.TextUtils;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import java.net.URISyntaxException;
public class SocketHelper {
    String url="";
    public SocketHelper(String urlToSendTo){
            url=urlToSendTo;
    }
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {}
    }
    public void connect() {
        mSocket.connect();
    }
    public void attemptSend(String message, String data) {
        if (TextUtils.isEmpty(message)&&TextUtils.isEmpty(data)) {
            return;
        }
        mSocket.emit(message, data);
    }
}
//https://swan-exp-eval.herokuapp.com