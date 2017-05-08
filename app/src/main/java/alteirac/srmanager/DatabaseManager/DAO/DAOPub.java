package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Pub;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOPub extends DAOAbstract implements DatabaseConstants {

    public DAOPub(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long add(Entity entity) {

        Pub pub = (Pub) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(pub);

        try {
            rowID = db.insert(TABLE_PUB, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Pub pubObj = new Pub();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_PUB,
                    new String[] { PUB_ID, PUB_NAME, PUB_LAT, PUB_LNG, PUB_IMAGE},
                    PUB_ID + "=" + id, null, null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    prepareGetData(pubObj, cursor);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return pubObj;
    }

    @Override
    public int update(Entity entity) {
        Pub pub = (Pub) entity;
        int count = -1;
        ContentValues values = prepareAddData(pub);

        String whereClause = PUB_ID + "=?";
        String whereArgs[] = new String[] { String.valueOf(pub.getId()) };

        try {
            count = db.update(TABLE_PUB, values, whereClause, whereArgs);
        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        try {
            count = db.delete(TABLE_PUB, PUB_ID + "=" + id, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

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



