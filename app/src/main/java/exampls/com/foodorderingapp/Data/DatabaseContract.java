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
        public static String resName = "resName";
        public static String resImg = "resImg";
        public static String resLat= "resLat";
        public static String resLang= "resLang";
        public static String minOrder = "resMinOrder";
        public static String deliveryFee = "resDeliveryFee";

    }


    public static final class TableRateColumns implements BaseColumns {
        public static String TABLE_NAME = "RateTable";

        public static final String _ID = "rateId";
        public static final String resId= "resId";
        public static final String RateNumOfPeople= "rateNumOfPeople";
        public static final String RateFullRateValue= "fullRateValue";

    }


    public static final class TableReviewColumns implements BaseColumns {
        public static String TABLE_NAME = "ReviewTable";

        public static final String _ID = "reviewId";
        public static final String rateId= "rateId";
        public static final String reviewDate= "reviewDate";
        public static final String reviewName = "reviewName";
        public static final String reviewRate= "reviewRate";
        public static final String review= "review";

    }


    public static final class TableMenItemuColumns implements BaseColumns {
        public static String TABLE_NAME = "MenuItemTable";

        public static final String _ID = "menuItemId";
        public static final String resId = "resId";
        public static final String name = "name";

    }


    public static final class TableSubCategoryColumns implements BaseColumns {
        public static String TABLE_NAME = "SubCategoryTable";

        public static final String _ID = "subCategoryId";
        public static final String menuItemId = "menuItemId";
        public static final String subCategoryName= "subCategoryName";
        public static final String subCategoryBasicPrice= "basicPrice";
        public static final String subCategoryImg= "categoryImg";
        public static final String subCategoryDescription= "categoryDescription";

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
