package exampls.com.foodorderingapp.Utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;

import exampls.com.foodorderingapp.Data.DatabaseContract;
import exampls.com.foodorderingapp.MyWidgeProvider;

/**
 * Created by 450 G1 on 06/03/2018.
 */

public class MyService extends IntentService {
    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        String newName = intent.getStringExtra("name");
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.TableRestaurantColumns.resName, newName);
        String[] projections = new String[]{DatabaseContract.TableRestaurantColumns._ID, DatabaseContract.TableRestaurantColumns.resName};
        Cursor cr = getApplicationContext().getContentResolver().query(DatabaseContract.CONTENT_URI, projections, null, null, null);
        try {
            if (cr.getCount() > 0) {
                // update
                getApplicationContext().getContentResolver().update(DatabaseContract.CONTENT_URI, contentValues, null, null);
            } else {

                // insert
                getApplicationContext().getContentResolver().insert(DatabaseContract.CONTENT_URI, contentValues);
            }
/*

            int widgetIDs[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), MyWidgeProvider.class));

            for (int id : widgetIDs)
                AppWidgetManager.getInstance(getApplication()).notifyAppWidgetViewDataChanged(id, R.id.name_widget);
*/
            notifyWdget();


    } finally {
            cr.close();
        }
    }


    public void notifyWdget(){
        Intent intent = new Intent(getApplicationContext(), MyWidgeProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(new ComponentName(getApplicationContext(), MyWidgeProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getApplicationContext().sendBroadcast(intent);

    }
}
