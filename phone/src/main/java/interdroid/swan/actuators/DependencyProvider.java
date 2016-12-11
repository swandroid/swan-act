package interdroid.swan.actuators;

import android.content.Context;

import org.droidparts.AbstractDependencyProvider;
import org.droidparts.net.http.RESTClient;
import org.droidparts.persist.sql.AbstractDBOpenHelper;

import interdroid.swan.actuators.database.ActionManager;
import interdroid.swan.actuators.database.DBOpenHelper;
import interdroid.swan.actuators.database.RuleManager;
import interdroid.swan.actuators.database.SwanExpManager;


public class DependencyProvider extends AbstractDependencyProvider{

    private final DBOpenHelper mDBOpenHelper;
    private final RESTClient mRESTClient;
    private final Context mContext;

    public DependencyProvider(Context ctx) {
        super(ctx);
        mDBOpenHelper = new DBOpenHelper(ctx);
        mRESTClient = new RESTClient(ctx);
        mContext = ctx;
    }

    @Override
    public AbstractDBOpenHelper getDBOpenHelper() {
        return mDBOpenHelper;
    }

    public RESTClient getRESTClient() {
        return mRESTClient;
    }

    public Context getContext() {
        return mContext;
    }

    public ActionManager getActionManager() {
        return new ActionManager(mContext);
    }

    public RuleManager getRuleManager() {
        return new RuleManager(mContext);
    }

    public SwanExpManager getPuckManager() {
        return new SwanExpManager(mContext);
    }

}
