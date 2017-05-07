package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.News;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAONews extends DAOAbstract implements DatabaseConstants {

    public DAONews(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    protected ContentValues prepareAddData(Entity entityObj) {

        News newsObj = (News) entityObj;
        ContentValues values = new ContentValues();
        values.put(NEWS_TITLE, newsObj.getTitle());
        values.put(NEWS_DATE, newsObj.getDate().getTime());
        values.put(NEWS_DESC, newsObj.getDescription());
        values.put(NEWS_IMAGE, newsObj.getByteImage());
        return values;
    }

    @Override
    protected void prepareGetData(Entity entityObj, Cursor cursor) {

        News newsObj = (News) entityObj;
        newsObj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(NEWS_ID)));
        newsObj.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(NEWS_TITLE)));
        newsObj.setDate(new Date(TimeUnit.SECONDS.toMillis(cursor.getLong(cursor.getColumnIndexOrThrow(NEWS_DATE)))));
        newsObj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(NEWS_DESC)));
        newsObj.setByteImage(cursor.getBlob(cursor.getColumnIndexOrThrow(NEWS_IMAGE)));
    }

    @Override
    public long add(Entity entity) {

        News news = (News) entity;
        long row = 0;
        ContentValues values = prepareAddData(news);

        try {
            row = db.insert(TABLE_NEWS, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return row;
    }

    @Override
    public Entity get(int id) {
        News newsObj = new News();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_NEWS,
                            new String[] { NEWS_ID, NEWS_TITLE, NEWS_DATE, NEWS_DESC, NEWS_IMAGE},
                            NEWS_ID + "=" + id, null, null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    prepareGetData(newsObj, cursor);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return newsObj;
    }

    public List<News> getAllNews() {
        ArrayList<News> allNewsObj = new ArrayList<News>();
        Cursor cursor;
        News newsObj;

        try {

            cursor = db.query(TABLE_NEWS,
                            new String[] { NEWS_ID, NEWS_TITLE, NEWS_DATE, NEWS_DESC, NEWS_IMAGE },
                            null, null, null, null, null);
            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    newsObj = new News();
                    prepareGetData(newsObj, cursor);
                    allNewsObj.add(newsObj);

                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return allNewsObj;
    }

    @Override
    public int update(Entity entity) {

        News news = (News) entity;
        int count = -1;
        ContentValues values = prepareAddData(news);

        String whereClause = NEWS_ID + "=?";
        String whereArgs[] = new String[] { String.valueOf(news.getId()) };

        count = db.update(TABLE_NEWS, values, whereClause, whereArgs);
        return count;
    }

    @Override
    public int delete(int id) {
        int count = 0;

        try {
            count = db.delete(TABLE_NEWS, NEWS_ID + "=" + id, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return count;
    }
}
