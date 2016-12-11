package interdroid.swan.actuators.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.droidparts.adapter.holder.ViewHolder;
import org.droidparts.annotation.inject.InjectView;

import interdroid.swan.R;

public class ExpressionHolder extends ViewHolder {

    @InjectView(id = R.id.mRuleName)
    public TextView mRuleName;

    /*@InjectView(id = R.id.llTriggerList)
    public LinearLayout mLlTriggerList;*/

    @InjectView(id = R.id.btnDelete)
    public Button mBtnDelete;

    @InjectView(id = R.id.btnAddRule)
    public Button mBtnAddRuleToPuck;

    public ExpressionHolder(View view) {
        super(view);
    }
}

