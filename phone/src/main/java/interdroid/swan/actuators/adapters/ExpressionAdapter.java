package interdroid.swan.actuators.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;

import interdroid.swan.R;
import interdroid.swan.actuators.database.RuleManager;
import interdroid.swan.actuators.database.SwanExpManager;
import interdroid.swan.actuators.entities.SwanExp;

import org.droidparts.adapter.cursor.EntityCursorAdapter;
import org.droidparts.annotation.inject.InjectDependency;
import org.droidparts.persist.sql.stmt.Select;

import java.util.HashMap;
import java.util.Map;


public class ExpressionAdapter extends EntityCursorAdapter<SwanExp> {

    @InjectDependency
    private RuleManager mRuleManager;

    @InjectDependency
    private SwanExpManager mSwanExpManager;

    private Map<String, Integer> connectionStates;


    public ExpressionAdapter(Context ctx, Select<SwanExp> select) {
        super(ctx, new SwanExpManager(ctx), select);
        //mClosestPuck = null;
        connectionStates = new HashMap<>();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view =  getLayoutInflater().inflate(R.layout.expression_list,null);
        ExpressionHolder puckViewHolder = new ExpressionHolder(view);
        view.setTag(puckViewHolder);
        return view;
    }

    @Override
    public void bindView(final Context context, View view, final SwanExp swanExp) {
        entityManager.fillForeignKeys(swanExp);

        final ExpressionHolder holder = (ExpressionHolder) view.getTag();
        StringBuilder puckName = new StringBuilder(swanExp.getName());
        puckName.setCharAt(0, Character.toUpperCase(puckName.charAt(0)));
        holder.mRuleName.setText(puckName.toString());

        int color = context.getResources().getColor(android.R.color.black);

        holder.mRuleName.setTextColor(color);

       /* RuleAdapter ruleAdapter = new RuleAdapter(context, mRuleManager.getRulesForPuck(swanExp));
        holder.mLlTriggerList.removeAllViews();
        for (int i=0; i< ruleAdapter.getCount(); i++) {
            View item = ruleAdapter.getView(i, null, null);
            holder.mLlTriggerList.addView(item);
        }*/

       holder.mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.remove_puck, swanExp.getName()))
                        .setPositiveButton(context.getString(R.string.remove), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mSwanExpManager.delete(swanExp.id)) {
                                    requeryData();
                                }
                            }
                        })
                        .setNegativeButton(context.getString(R.string.abort), null);
                builder.create().show();
            }
        });

    }

}
