package exampls.com.foodorderingapp.Utils;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;

import exampls.com.foodorderingapp.MyWidgeProvider;
import exampls.com.foodorderingapp.Realm.WidgetTable;
import io.realm.Realm;

/**
 * Created by 450 G1 on 06/03/2018.
 */

public class MyService extends IntentService {
    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        WidgetTable widgetTable = new WidgetTable();
        widgetTable.setName(intent.getStringExtra("name"));
        realm.copyToRealmOrUpdate(widgetTable);
        realm.commitTransaction();

        Intent intent1 = new Intent(getApplicationContext(), MyWidgeProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(new ComponentName(getApplicationContext(), MyWidgeProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getApplicationContext().sendBroadcast(intent1);
    }
}
