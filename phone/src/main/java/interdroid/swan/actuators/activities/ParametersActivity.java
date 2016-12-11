package interdroid.swan.actuators.activities;

import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.common.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interdroid.swan.R;
import interdroid.swan.actuators.ActuatorManager;

public class ParametersActivity extends Activity {

    LinearLayout containerLayout;
    static int totalEditTexts = 0;
    Button button;
    Context context;
    public static int length;
    List<EditText> allEds = new ArrayList<EditText>();
    String[] params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        containerLayout=(LinearLayout)findViewById(R.id.activity_parameters);
        Bundle extras = getIntent().getExtras();

        Button button = (Button)findViewById(R.id.submit);
        button.setOnClickListener(submit);

        EditText editText;

        if (extras != null) {
            length = extras.getInt("length");
            params=new String[length];
            for(int i=0;i<length;i++){
                params[i]=extras.getString("parameter"+i);
                editText = new EditText(getBaseContext());
                allEds.add(editText);
                containerLayout.addView(editText);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) editText.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                layoutParams.setMargins(23, 34, 0, 0);
                editText.setLayoutParams(layoutParams);
                editText.setText(params[i]);
                editText.setTag(params[i]);
                editText.setId(i);
            }//The key argument here must match that used in the other activity
        }
    }
    private View.OnClickListener submit = new View.OnClickListener() {
        public void onClick(View v) {
            String[] filledParams = new String[length];

            for(int i=0; i < allEds.size(); i++){
                filledParams[i] = allEds.get(i).getText().toString();
                ActuatorManager.parameters.put(params[i],filledParams[i]);

            }
            finish();
        }
    };
}
