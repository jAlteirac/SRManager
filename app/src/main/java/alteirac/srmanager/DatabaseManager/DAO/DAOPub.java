package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Pub;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOPub extends DAOAbstract implements DatabaseConstants {

    public DAOPub(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public long add(Entity entity) {

        Pub pub = (Pub) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(pub);

        Uri uriAdd = contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_PUB, values);
        String lastPathSegment = uriAdd.getLastPathSegment();
        if (lastPathSegment != null) {
            rowID = Long.valueOf(lastPathSegment);
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Pub pubObj = new Pub();
        Cursor cursor;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_PUB, id);
        cursor = contentResolver.query(uri, new String[] { PUB_ID, PUB_NAME, PUB_LAT, PUB_LNG, PUB_IMAGE}, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                prepareGetData(pubObj, cursor);
            } while (cursor.moveToNext());
        }

        return pubObj;
    }

    @Override
    public int update(Entity entity) {
        Pub pub = (Pub) entity;
        int count = -1;
        ContentValues values = prepareAddData(pub);

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_PUB, pub.getId());
        count = contentResolver.update(uri, values, null, null);

        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_PUB, id);
        count = contentResolver.delete(uri, null, null);

        return count;
    }

    @Override
    protected ContentValues prepareAddData(Entity entityObj) {
        Pub pubObj = (Pub) entityObj;
        ContentValues values = new ContentValues();
        values.put(PUB_NAME, pubObj.getName());
        values.put(PUB_LAT, pubObj.getLat());
        values.put(PUB_LNG, pubObj.getLng());
        values.put(PUB_IMAGE, pubObj.getByteImage());
        return values;
    }

    @Override
    protected void prepareGetData(Entity entityObj, Cursor cursor) {
        Pub pubObj = (Pub) entityObj;
        pubObj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PUB_ID)));
        pubObj.setName(cursor.getString(cursor.getColumnIndexOrThrow(PUB_NAME)));
        pubObj.setLat(cursor.getFloat(cursor.getColumnIndexOrThrow(PUB_LAT)));
        pubObj.setLng(cursor.getFloat(cursor.getColumnIndexOrThrow(PUB_LNG)));
        pubObj.setByteImage(cursor.getBlob(cursor.getColumnIndexOrThrow(PUB_IMAGE)));
    }
}



