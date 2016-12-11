package interdroid.swan.actuators;

import android.os.Bundle;

import interdroid.swan.R;
import interdroid.swancore.RegisterActuators;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


public class ActuatorsActivity extends AppCompatActivity {

    public static RegisterActuators registerActuators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuators);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        registerActuators=new RegisterActuators();
        registerActuators.loadActuators(this);


        //FOR BROADCAST
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("swan-actuation-data-received"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String expression = intent.getStringExtra("expression");
            String type=intent.getStringExtra("type");
            String parameters=intent.getStringExtra("parameters");
            registerActuators.loadConfig(expression,parameters);
            Log.d("receiver", "Got message: " + expression);
        }
    };
}
