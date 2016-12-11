package interdroid.swan.actuators.swan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.inject.InjectDependency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import interdroid.swan.actuators.ActuatorManager;
import interdroid.swancore.swanmain.ExpressionManager;
import interdroid.swancore.swanmain.SwanException;
import interdroid.swancore.swanmain.TriStateExpressionListener;
import interdroid.swancore.swanmain.ValueExpressionListener;
import interdroid.swancore.swansong.Expression;
import interdroid.swancore.swansong.ExpressionFactory;
import interdroid.swancore.swansong.ExpressionParseException;
import interdroid.swancore.swansong.TimestampedValue;
import interdroid.swancore.swansong.TriState;
import interdroid.swancore.swansong.TriStateExpression;
import interdroid.swancore.swansong.ValueExpression;

/**
 * Created by rm on 26/10/2016.
 */

public class SwanSensor {
    @InjectDependency
    Context mContext;
    String triggerToUnregister="null";
    String expression="";
    String registeredTrigger="";
    List<String> triggers;
    final Queue<String> queue = new ConcurrentLinkedQueue<String>();
    public SwanSensor(){
        Injector.inject(Injector.getApplicationContext(), this);
    }

    // Buffer
    public String currentValue="null";
    public PipedOutputStream pipedOutputStream=new PipedOutputStream();

    public int registerVirtualSensor(final Context context, final String myExpression, String trigger){
        triggers=new ArrayList<String>();
        this.triggers.add(trigger);
        return 1;
    }

    public void remoteStream(String data){
        setValue(data);
    }

    public int registerSWANSensor(final Context context, final String myExpression, final String trigger) {
        this.expression=myExpression;
        this.registeredTrigger=trigger;
        triggers=new ArrayList<String>();
        this.triggers.add(trigger);

        /*//triggers.add(trigger);
        pipedOutputStream =  new PipedOutputStream();
        final Integer[] buf =new Integer[10];
        final byte[] buffer = new byte[1024];
        //Queue*/

        ActuatorManager.runningExpressions.put(myExpression,trigger);
        String expressionId=myExpression;

        int expressionCheckTri=0;
        int expressionCheckVal=0;


        try {
            Expression check=ExpressionFactory.parse(myExpression);
            String [] valueCheck=check.toString().split("Value");
            if(valueCheck.length>1){
                expressionCheckVal=1;
            }
            else{
                expressionCheckTri=1;
            }
        }
        catch (ExpressionParseException e){
        }

        if(expressionCheckTri==1) {
            try {
                ExpressionManager.registerTriStateExpression(context, String.valueOf(trigger),
                        (TriStateExpression) ExpressionFactory.parse(myExpression),
                        new TriStateExpressionListener() {
                            @Override
                            public void onNewState(String arg0, long arg1, TriState arg2) {
                                if (arg2 == TriState.TRUE) {
                                    setValue("TRUE");
                                } else if (arg2 == TriState.FALSE) {
                                    setValue("FALSE");
                                } else if (arg2 == TriState.UNDEFINED) {
                                    setValue("UNDEFINED");
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
        else if(expressionCheckVal==1) {
            try {
                ExpressionManager.registerValueExpression(context, String.valueOf(trigger),
                        (ValueExpression) ExpressionFactory.parse(myExpression),
                        new ValueExpressionListener() {

                            //Registering a listener to process new values from the registered sensor
                            @Override
                            public void onNewValues(String id,
                                                    TimestampedValue[] arg1) {
                                if (arg1 != null && arg1.length > 0) {
                                    String value = arg1[0].getValue().toString();
                                    sendBroadcast(value);
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
            return 1;
        }
        return 0;
    }
    public void setValue(String value){
        //queue.offer("TRUE");
        for(String trigger : triggers){
            Intent intent = new Intent(trigger);
            // You can also include some extra data.
            intent.putExtra("data",value);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
    }
    public void sendBroadcast(String value){
        for(String trigger : triggers){
            Intent intent = new Intent(trigger);
            // You can also include some extra data.
            intent.putExtra("data",value);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }
    }
    public void unregisterActuator(String unregisterTrigger){
        if(ActuatorManager.runningExpressionsCount.get(this.expression)>0){
            this.triggerToUnregister=unregisterTrigger;
            Integer expressionCount=ActuatorManager.runningExpressionsCount.get(this.expression);
            setValue("unregister");
            ActuatorManager.runningExpressionsCount.remove(this.expression);
            ActuatorManager.runningExpressionsCount.put(this.expression,expressionCount-1);
        }
        else{
            this.triggerToUnregister=unregisterTrigger;
            ExpressionManager.unregisterExpression(this.mContext,ActuatorManager.runningExpressions.get(this.expression));
            setValue("unregister");
            ActuatorManager.runningExpressions.remove(this.expression);
            ActuatorManager.runningExpressionsCount.remove(this.expression);
        }
    }
    public String checkRegistrationStatus(){
        return triggerToUnregister;
    }
    public String getRegisteredTrigger(){return this.registeredTrigger;}
    public void registerMultipleForSameExpression(String trigg){
        triggers.add(trigg);
    }
}
