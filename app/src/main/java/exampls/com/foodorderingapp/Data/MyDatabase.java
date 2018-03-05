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
            DatabaseContract.TableRestaurantColumns.resName + " TEXT, " +
            DatabaseContract.TableRestaurantColumns.resImg + " TEXT, " +
            DatabaseContract.TableRestaurantColumns.resLat + " TEXT, " +
            DatabaseContract.TableRestaurantColumns.resLang + " TEXT, " +
            DatabaseContract.TableRestaurantColumns.minOrder + " Integer, " +
            DatabaseContract.TableRestaurantColumns.deliveryFee + " Integer )";


    // Tag table create statement
    private static final String CREATE_TABLE_RATE =" create table "+DatabaseContract.TableRateColumns.TABLE_NAME+" ( "+
            DatabaseContract.TableRateColumns._ID+" INTEGER PRIMARY KEY, "+
            DatabaseContract.TableRateColumns.resId+" INTEGER , "+
            DatabaseContract.TableRateColumns.RateNumOfPeople+" INTEGER , "+
            DatabaseContract.TableRateColumns.RateFullRateValue+"  REAL )";
    // todo_tag table create statement
    private static final String CREATE_TABLE_REVIEW =" create table "+ DatabaseContract.TableReviewColumns.TABLE_NAME+" ( "+
            DatabaseContract.TableReviewColumns._ID+" INTEGER PRIMARY KEY, "+
            DatabaseContract.TableReviewColumns.rateId+" INTEGER , "+
            DatabaseContract.TableReviewColumns.reviewDate+" TEXT , "+
            DatabaseContract.TableReviewColumns.reviewName+" TEXT , "+
            DatabaseContract.TableReviewColumns.reviewRate+" REAL , "+
            DatabaseContract.TableReviewColumns.review+" TEXT)";



    // Tag table create statement
    private static final String CREATE_TABLE_MENU_ITEM_TABLE = "CREATE TABLE " + DatabaseContract.TableMenItemuColumns.TABLE_NAME+" ( "+
            DatabaseContract.TableMenItemuColumns.resId+" INTEGER, "+
            DatabaseContract.TableMenItemuColumns._ID+" INTEGER PRIMARY KEY , "+
            DatabaseContract.TableMenItemuColumns.TABLE_NAME+" TEXT )";

    // todo_tag table create statement
    private static final String CREATE_TABLE_SUBCATEGORY = "CREATE TABLE "+DatabaseContract.TableSubCategoryColumns.TABLE_NAME+" ( "+
            DatabaseContract.TableSubCategoryColumns._ID+" INTEGER PRIMARY KEY , "+
            DatabaseContract.TableSubCategoryColumns.menuItemId+ " INTEGER , "+
            DatabaseContract.TableSubCategoryColumns.subCategoryName+ " TEXT , "+
            DatabaseContract.TableSubCategoryColumns.subCategoryBasicPrice+ " REAL, "+
            DatabaseContract.TableSubCategoryColumns.subCategoryImg+ " TEXT, "+
            DatabaseContract.TableSubCategoryColumns.subCategoryDescription+" TEXT )";


    public MyDatabase(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_RATE);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_MENU_ITEM_TABLE);
        db.execSQL(CREATE_TABLE_SUBCATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableRestaurantColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableRateColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableReviewColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableMenItemuColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TableSubCategoryColumns.TABLE_NAME);
        onCreate(db);
    }
}