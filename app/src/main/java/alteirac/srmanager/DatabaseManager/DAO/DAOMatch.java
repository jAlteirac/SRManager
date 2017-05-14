package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.Model.Pub;
import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOMatch extends DAOAbstract implements DatabaseConstants {


    public DAOMatch(SQLiteDatabase db) {
        this.db = db;
    }

    public Match getLastMatch() {

        Cursor cursor;

        try {
            cursor = db.rawQuery("SELECT MAX(" + MATCH_ID + ") AS MAX FROM " + TABLE_MATCH, null);
            int id = -1;
            if (cursor != null) {
                cursor.moveToFirst();
                if (!cursor.isAfterLast()) {
                    do {
                        int i = cursor.getInt(cursor.getColumnIndex("MAX"));
                        Match match = (Match)get(i);
                        return match;
                    } while (cursor.moveToNext());
                }
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public long add(Entity entity) {
        Match matchObj = (Match) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(matchObj);

        try {
            rowID = db.insert(TABLE_MATCH, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        ArrayList<Pub> pubs = matchObj.getPubs();
        Iterator<Pub> it = pubs.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(MP_MATCH, rowID);
            values_tp.put(MP_PUB, it.next().getId());
            try {
                db.insert(TABLE_MP, null, values_tp);
            } catch (Exception e) {
                Log.e("DB ERROR", e.toString());
                e.printStackTrace();
            }
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Match matchObj = new Match();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_MATCH,
                    new String[] { MATCH_ID, MATCH_DATE, MATCH_LOCATION, MATCH_REFEREE, MATCH_TEAM1, MATCH_TEAM2},
                    MATCH_ID + "=" + id, null, null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    prepareGetData(matchObj, cursor);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return matchObj;
    }

    @Override
    public int update(Entity entity) {
        Match matchObj = (Match) entity;
        int count = -1;
        ContentValues values = prepareAddData(matchObj);

        String whereClause = MATCH_ID + "=?";
        String whereArgs[] = new String[] { String.valueOf(matchObj.getId()) };

        try {
            db.delete(TABLE_MP, MP_MATCH + "=" + matchObj.getId(), null);
            count = db.update(TABLE_MATCH, values, whereClause, whereArgs);
        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        ArrayList<Pub> pubs = matchObj.getPubs();
        Iterator<Pub> it = pubs.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(MP_MATCH, matchObj.getId());
            values_tp.put(MP_PUB, it.next().getId());
            try {
                db.insert(TABLE_MP, null, values_tp);
            } catch (Exception e) {
                Log.e("DB ERROR", e.toString());
                e.printStackTrace();
            }
        }

        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        try {
            db.delete(TABLE_MP, MP_MATCH + "=" + id, null);
            count = db.delete(TABLE_MATCH, MATCH_ID + "=" + id, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return count;
    }

    @Override
    protected ContentValues prepareAddData(Entity entityObj) {

        Match matchObj = (Match) entityObj;
        ContentValues values = new ContentValues();
        values.put(MATCH_DATE, matchObj.getDate().getTime());
        values.put(MATCH_LOCATION, matchObj.getLocation());
        values.put(MATCH_REFEREE, matchObj.getReferee());
        values.put(MATCH_TEAM1, matchObj.getTeam1().getId());
        values.put(MATCH_TEAM2, matchObj.getTeam2().getId());
        return values;
    }

    @Override
    protected void prepareGetData(Entity entityObj, Cursor cursor) {
        Match matchObj = (Match) entityObj;
        matchObj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MATCH_ID)));
        Cursor cursorTmp;
        ArrayList<Pub> pubs = new ArrayList<Pub>();
        DAOPub daoPub = new DAOPub(db);

        try {
            cursorTmp = db.query(TABLE_MP,
                    new String[] { MP_PUB},
                    MP_MATCH + "=" + matchObj.getId(), null, null, null, null, null);
            cursorTmp.moveToFirst();
            if (!cursorTmp.isAfterLast()) {
                do {
                    pubs.add((Pub)daoPub.get(cursorTmp.getInt(0)));
                } while (cursorTmp.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        DAOTeam daoTeam = new DAOTeam(db);

        matchObj.setDate(new Date(TimeUnit.SECONDS.toMillis(cursor.getLong(cursor.getColumnIndexOrThrow(MATCH_DATE)))));
        matchObj.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(MATCH_LOCATION)));
        matchObj.setReferee(cursor.getString(cursor.getColumnIndexOrThrow(MATCH_REFEREE)));
        matchObj.setTeam1((Team)daoTeam.get(cursor.getInt(cursor.getColumnIndexOrThrow(MATCH_TEAM1))));
        matchObj.setTeam2((Team)daoTeam.get(cursor.getInt(cursor.getColumnIndexOrThrow(MATCH_TEAM2))));
        matchObj.setPubs(pubs);
    }
}
