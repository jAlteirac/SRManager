package alteirac.srmanager.DatabaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jean on 07/05/2017.
 */

public class DatabaseManager implements  DatabaseConstants {


    private SQLiteDatabase db;

    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;

        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        this.db = helper.getWritableDatabase();
    }

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String create_table_news =
                    "create table " + TABLE_NEWS + " ("
                    + NEWS_ID + " integer primary key autoincrement not null,"
                    + NEWS_TITLE + " text not null,"
                    + NEWS_DATE + " integer not null,"
                    + NEWS_SHORT_DESC + " text not null,"
                    + NEWS_LONG_DESC + " text not null,"
                    + NEWS_IMAGE + " blob" + ");";

            String create_table_pub =
                    "create table " + TABLE_PUB + " ("
                    + PUB_ID + " integer primary key autoincrement not null,"
                    + PUB_NAME + " text not null,"
                    + PUB_LAT + " real not null,"
                    + PUB_LNG + " real not null,"
                    + PUB_IMAGE + " blob" + ");";


            db.execSQL(create_table_news);
            db.execSQL(create_table_pub);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUB);

            onCreate(db);
        }

        }

    }

    public SQLiteDatabase getDataBase() {
        return db;
    }
}
