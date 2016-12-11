package interdroid.swan.actuators.activities;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rm on 17/10/2016.
 */

public class ParamterNotSpecified {
    public void checkParameters(Context context, String parameters) {
        Intent intent=new Intent(context,ParametersActivity.class);
        String[] parametersList = parameters.split(",");
        for (int i = 0; i < parametersList.length; i++) {
            intent.putExtra("length",parametersList.length);
            intent.putExtra("parameter"+i, parametersList[i]);
        }
        context.startActivity(intent);
    }
}
