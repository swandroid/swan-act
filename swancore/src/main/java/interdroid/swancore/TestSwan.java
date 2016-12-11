package interdroid.swancore;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import java.util.Random;

import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SwanException;
import interdroid.swancore.swanmain.ValueExpressionListener;
import interdroid.swancore.swansong.ExpressionFactory;
import interdroid.swancore.swansong.ExpressionParseException;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.ValueExpression;
import rx.Scheduler;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by rm on 15/10/2016.
 */

public class TestSwan {

    Random rand=new Random();

    int randomId = rand.nextInt((2000 - 0) + 1) + 0;
    String expressionId="self@light:lux{MAX,10ms}";

    Context context;

    public void swanService(Context context){
        this.context=context;
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
                                //actuate(value,v);
                                //mCircularQueue.expressionQueue(value);
                                Log.d("CORE VALUES", value.toString());
                                //Log.d("SENSOR TIME", timestamp.toString());
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
    /*public void actuate(String value,Vibrator v) {
        if (value != null) {
            final String s = value;
            final Scheduler.Worker worker = Schedulers.io().createWorker();
            worker.schedule(() -> {
                ring();
                // Vibrate for 500 milliseconds
                worker.unsubscribe();
            });
        }
    }*/

    public void ring(){
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(5000);
    }
}
