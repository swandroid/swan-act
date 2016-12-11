package interdroid.swan.actuators.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import interdroid.swan.R;
import interdroid.swan.actuators.actuation.Actuate;
import interdroid.swan.actuators.database.RuleManager;
import interdroid.swan.actuators.entities.SwanActRule;

import org.droidparts.adapter.cursor.EntityCursorAdapter;
import org.droidparts.bus.EventBus;
import org.droidparts.persist.sql.stmt.Select;


public class RuleAdapter extends EntityCursorAdapter<SwanActRule> {

    public RuleAdapter(Context ctx, Select<SwanActRule> select) {
        super(ctx, new RuleManager(ctx), select);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view =  getLayoutInflater().inflate(R.layout.trigger_list_item, null);
        TriggerViewHolder holder = new TriggerViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(Context context, View view, final SwanActRule swanActRule) {
        entityManager.fillForeignKeys(swanActRule);

        TriggerViewHolder holder = (TriggerViewHolder) view.getTag();

        StringBuilder sb = new StringBuilder();

        sb.setLength(Math.max(sb.length() -1, 0));


        holder.btnRemoveRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.postEvent(Actuate.TRIGGER_REMOVE_RULE, swanActRule);
            }
        });
    }
}
