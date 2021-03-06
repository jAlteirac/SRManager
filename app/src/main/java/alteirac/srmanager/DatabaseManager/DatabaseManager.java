package alteirac.srmanager.DatabaseManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jean on 07/05/2017.
 */

public final class DatabaseManager implements  DatabaseConstants {


    private static volatile DatabaseManager instance = null;
    private SQLiteDatabase db;

    private DatabaseManager(Context context) {

        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        this.db = helper.getWritableDatabase();
    }

    public final static DatabaseManager getInstance() {
        return getInstance(null);
    }

    public final static DatabaseManager getInstance(final Context context) {
        if (DatabaseManager.instance == null) {
            synchronized (DatabaseManager.class) {
                if(DatabaseManager.instance == null) {
                    DatabaseManager.instance = new DatabaseManager(context);
                }
            }
        }
        return DatabaseManager.instance;
    }

    public SQLiteDatabase getDataBase() {
        return db;
    }

    public void DeleteAll() {

        db.execSQL("DELETE FROM " + TABLE_MP);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_MP + "'");
        db.execSQL("DELETE FROM " + TABLE_TP);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_TP + "'");
        db.execSQL("DELETE FROM " + TABLE_PUB);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_PUB + "'");
        db.execSQL("DELETE FROM " + TABLE_NEWS);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_NEWS + "'");
        db.execSQL("DELETE FROM " + TABLE_MATCH);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_MATCH + "'");
        db.execSQL("DELETE FROM " + TABLE_TEAM);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME='" + TABLE_TEAM + "'");

    }


    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

        public CustomSQLiteOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String create_table_team =
                    "create table " + TABLE_TEAM + " ("
                    + TEAM_ID + " integer primary key autoincrement not null,"
                    + TEAM_NAME + " text not null,"
                    + TEAM_IMAGE + " blob" + ");";

            String create_table_match =
                    "create table " + TABLE_MATCH + " ("
                    + MATCH_ID + " integer primary key autoincrement not null,"
                    + MATCH_DATE + " integer not null,"
                    + MATCH_LOCATION + " text not null,"
                    + MATCH_REFEREE + " text not null,"
                    + MATCH_TEAM1 + " integer not null,"
                    + MATCH_TEAM2 + " integer not null" + ");";


            String create_table_news =
                    "create table " + TABLE_NEWS + " ("
                    + NEWS_ID + " integer primary key autoincrement not null,"
                    + NEWS_TITLE + " text not null,"
                    + NEWS_DATE + " integer not null,"
                    + NEWS_DESC + " text not null,"
                    + NEWS_IMAGE + " blob" + ");";

            String create_table_pub =
                    "create table " + TABLE_PUB + " ("
                    + PUB_ID + " integer primary key autoincrement not null,"
                    + PUB_NAME + " text not null,"
                    + PUB_LAT + " real not null,"
                    + PUB_LNG + " real not null,"
                    + PUB_IMAGE + " blob" + ");";

            String create_table_match_pub =
                    "create table " + TABLE_MP + " ("
                    + MP_MATCH + " integer not null,"
                    + MP_PUB + " integer not null" + ");";

            String create_table_team_player =
                    "create table " + TABLE_TP + " ("
                    + TP_TEAM + " integer not null,"
                    + TP_PLAYER + " text not null" + ");";

            db.execSQL(create_table_team);
            db.execSQL(create_table_match);
            db.execSQL(create_table_news);
            db.execSQL(create_table_pub);
            db.execSQL(create_table_match_pub);
            db.execSQL(create_table_team_player);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TP);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUB);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);

            onCreate(db);
        }

        }

    }
