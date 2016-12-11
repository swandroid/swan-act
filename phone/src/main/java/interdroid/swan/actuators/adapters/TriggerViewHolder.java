package interdroid.swan.actuators.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.droidparts.adapter.holder.ViewHolder;
import org.droidparts.annotation.inject.InjectView;

import interdroid.swan.R;


public class TriggerViewHolder extends ViewHolder {

    @InjectView(id = R.id.btnRemoveRule)
    public Button btnRemoveRule;

    public TriggerViewHolder(View view) {
        super(view);
    }
}
