package interdroid.swan.actuators.events;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import org.droidparts.Injector;
import org.droidparts.annotation.bus.ReceiveEvents;
import org.droidparts.annotation.inject.InjectDependency;

import java.util.ArrayList;

import interdroid.swan.actuators.actuation.Actuate;
import interdroid.swan.actuators.entities.SwanExp;

import static interdroid.swan.actuators.ActuatorManager.mSwanExpManager;

/**
 * Created by rm on 27/10/2016.
 */

public class Events {

    @InjectDependency
    Context mContext;

    public Events(){
        Injector.inject(Injector.getApplicationContext(), this);
    }

    @ReceiveEvents(name= Actuate.TRIGGER_UPCOUNT)
    public void actuateUpcount(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage("TRIGGERED");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        final ArrayList<SwanExp> pucks = mSwanExpManager.getAll();
        Log.d("Rules",pucks.get(0).toString());
        Log.d("Rules Size","lesser");
        if(pucks.size() >0) {
            Log.d("Rules Size","greater");
            Log.d("DATA:",pucks.get(0).toString());
            new Thread() {
                @Override
                public void run() {
                    Actuate.trigger(pucks.get(0), "Upcount");
                }
            }.start();
        }
    }

    @ReceiveEvents(name=Actuate.TRIGGER_DOWNCOUNT)
    public void actuateCountdown(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage("TRIGGERED");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

        final ArrayList<SwanExp> rules = mSwanExpManager.getAll();
        Log.d("Rules",rules.get(0).toString());
        Log.d("Rules Size","lesser");
        if(rules.size() >0) {
            Log.d("Rules Size","greater");
            Log.d("DATA:",rules.get(0).toString());

            new Thread() {
                @Override
                public void run() {
                    Actuate.trigger(rules.get(1), "Countdown");
                }
            }.start();
        }
    }
}
