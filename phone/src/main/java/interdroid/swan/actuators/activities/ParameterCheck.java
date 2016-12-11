package interdroid.swan.actuators.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.api.client.util.StringUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by rm on 15/10/2016.
 */

public class ParameterCheck {
    /**
     *
     * @param context
     * @param parameters
     * @param params
     * @return
     */
    public HashMap<String,String> checkParameters(Context context, String parameters,String params) {
        Intent intent = new Intent(context, ParametersActivity.class);
        HashMap<String,String> parametersToSend =new HashMap<String, String>();
        String paramsCheckIfOnlyOne[] = params.split("\\&");
            for(int i=0;i<paramsCheckIfOnlyOne.length;i++) {
                String[] paramsList = paramsCheckIfOnlyOne[i].split("\\=");
                String param=paramsList[0];
                String paramValue="";
                Pattern p = Pattern.compile("[\"'](.+)[\"']");
                Matcher m = p.matcher(paramsList[1]);
                    while (m.find()) {
                        paramValue=m.group(1);
                    }
                parametersToSend.put(param,paramValue);
        }
    return parametersToSend;
    }
}

