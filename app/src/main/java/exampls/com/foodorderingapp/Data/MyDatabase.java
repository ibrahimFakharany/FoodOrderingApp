package exampls.com.foodorderingapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 450 G1 on 05/03/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    public static final String dbName = "RestaurantsDB";
    public static final int version = 1;

    private static final String CREATE_TABLE_RESTAURANT = " create table " + DatabaseContract.TableRestaurantColumns.TABLE_NAME + " ( " +
            DatabaseContract.TableRestaurantColumns._ID + " INTEGER PRIMARY KEY, " +
            DatabaseContract.TableRestaurantColumns.resName + " TEXT) ";

    public MyDatabase(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_RESTAURANT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableRestaurantColumns.TABLE_NAME);
        onCreate(db);
    }
}