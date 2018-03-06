package exampls.com.foodorderingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import java.util.List;

import exampls.com.foodorderingapp.Realm.WidgetTable;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by 450 G1 on 06/03/2018.
 */

public class MyWidgeProvider extends AppWidgetProvider {
    static RealmResults<WidgetTable> results;
    static List<WidgetTable> recipesArray;
    static Realm realm;
    public static  RemoteViews getRecipesListView(Context context){
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widge_provider);
        // getting from database

        realm.init(context);
        realm = Realm.getDefaultInstance();
        results = realm.where(WidgetTable.class).findAll();

        if(results.size() == 0){
            views.setTextViewText(R.id.name_widget, "please select recipe from the app");
            return views;
        }

        recipesArray = realm.copyFromRealm(results);
        realm.close();

        WidgetTable widgetTable = recipesArray.get(0);

        // update widget view
        views.setTextViewText(R.id.name_widget,widgetTable.getName());
        /*views.setTextViewText(R.id.ingredients_widget, widgetTable.getIngredients());
        Intent intent = new Intent(context, StepsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RecipesActivity.RECIPE_KEY, widgetTable.getId());
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,0);
        views.setOnClickPendingIntent(R.id.relative_widget, pendingIntent);*/
        return views;

    }
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = getRecipesListView(context.getApplicationContext());
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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