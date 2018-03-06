package exampls.com.foodorderingapp.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by 450 G1 on 05/03/2018.
 */

public class DatabaseContract {

    public static String TABLE_RESTAURANT = "RestaurantTable";

    public static final class TableRestaurantColumns implements BaseColumns {
        public static String TABLE_NAME = "RestaurantTable";
        public static final String _ID = "resId";
        public static final String resName = "resName";
    }







    /*static String CONTENT_AUTHORITY = "exampls.com.movieappplus.Model.Data";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static String PATH_FAVOURIT = "movies";

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURIT).build();*/




    public static final String CONTENT_AUTHORITY = "exampls.com.foodorderingapp.Data";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(CONTENT_AUTHORITY)
            .appendPath(TABLE_RESTAURANT)
            .build();


}
