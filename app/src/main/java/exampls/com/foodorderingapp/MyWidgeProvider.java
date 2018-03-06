package exampls.com.foodorderingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import exampls.com.foodorderingapp.Data.DatabaseContract;

/**
 * Created by 450 G1 on 06/03/2018.
 */

public class MyWidgeProvider extends AppWidgetProvider {

    private RemoteViews updateMyWidget(Context context, int appWidgetId) {

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widge_provider);
        Log.e("widget", "update my Widget");
        // getting from database
        String[] projections = new String[]{DatabaseContract.TableRestaurantColumns._ID, DatabaseContract.TableRestaurantColumns.resName};
        String name = null;
        Cursor cr = context.getContentResolver().query(DatabaseContract.CONTENT_URI, projections, null, null, null);
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            name = cr.getString(cr.getColumnIndex(DatabaseContract.TableRestaurantColumns.resName));
        }

        Log.e("widget", "cursor size "+ cr.getCount());

        // update widget view
        views.setTextViewText(R.id.name_widget, name);

        return views;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateMyWidget(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}