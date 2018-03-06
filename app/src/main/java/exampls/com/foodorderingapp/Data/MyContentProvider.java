package exampls.com.foodorderingapp.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by 450 G1 on 05/03/2018.
 */

public class MyContentProvider extends ContentProvider {

    private static final String TAG = MyContentProvider.class.getSimpleName();
    private static final int TASKS = 100;
    private static final int TASKS_WITH_ID = 101;


    Context context;
    MyDatabase database;


    @Override
    public boolean onCreate() {

        this.context = getContext();
        this.database = new MyDatabase(context);
        return true;

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseContract.TableRestaurantColumns.TABLE_NAME
                , projection
                , selection
                , selectionArgs
                , null
                , null
                , sortOrder);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable final ContentValues contentValues) {
        SQLiteDatabase db = database.getWritableDatabase();


        long i = db.insert(DatabaseContract.TableRestaurantColumns.TABLE_NAME, null, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, i);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db  =  database.getWritableDatabase();
        count = db.update(DatabaseContract.TableRestaurantColumns.TABLE_NAME, values, DatabaseContract.TableRestaurantColumns._ID +
                " = " +  1 , selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
