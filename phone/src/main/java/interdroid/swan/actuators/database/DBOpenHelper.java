package interdroid.swan.actuators.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import interdroid.swan.actuators.entities.Action;
import interdroid.swan.actuators.entities.SwanActRule;
import interdroid.swan.actuators.entities.SwanExp;

import org.droidparts.persist.sql.AbstractDBOpenHelper;


public class DBOpenHelper extends AbstractDBOpenHelper {

    private static final String DB_FILE = "SwanActuators.sql";
    private static final int DB_VERSION = 12;

    public DBOpenHelper(Context ctx) {
        super(ctx, DB_FILE, DB_VERSION);
    }

    @Override
    protected void onCreateTables(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase, SwanExp.class);
        createTables(sqLiteDatabase, Action.class);
        createTables(sqLiteDatabase, SwanActRule.class);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* TODO: add migration */
        dropTables(db);
        onCreate(db);
    }
}
