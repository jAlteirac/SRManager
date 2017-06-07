package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

    public DAONews(ContentResolver contentResolver) {
        super(contentResolver);
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

        News newsObj = (News) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(newsObj);

        Uri uriAdd = contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_NEWS, values);
        String lastPathSegment = uriAdd.getLastPathSegment();
        if (lastPathSegment != null) {
            rowID = Long.valueOf(lastPathSegment);
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        News newsObj = new News();
        Cursor cursor;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_NEWS, id);
        cursor = contentResolver.query(uri, new String[] { NEWS_ID, NEWS_TITLE, NEWS_DATE, NEWS_DESC, NEWS_IMAGE}, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                prepareGetData(newsObj, cursor);
            } while (cursor.moveToNext());
        }

        return newsObj;
    }

    public ArrayList<News> getAllNews() {
        ArrayList<News> allNewsObj = new ArrayList<News>();
        Cursor cursor;
        News newsObj;

        cursor = contentResolver.query(DatabaseConstants.CONTENT_URI_ALL_NEWS, new String[] { NEWS_ID, NEWS_TITLE, NEWS_DATE, NEWS_DESC, NEWS_IMAGE}, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                newsObj = new News();
                prepareGetData(newsObj, cursor);
                allNewsObj.add(newsObj);

            } while (cursor.moveToNext());
        }

        return allNewsObj;
    }

    @Override
    public int update(Entity entity) {

        News newsObj = (News) entity;
        int count = -1;
        ContentValues values = prepareAddData(newsObj);

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_NEWS, newsObj.getId());
        count = contentResolver.update(uri, values, null, null);

        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_NEWS, id);
        count = contentResolver.delete(uri, null, null);

        return count;
    }
}
