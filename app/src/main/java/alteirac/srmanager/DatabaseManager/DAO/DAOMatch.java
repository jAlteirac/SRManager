package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.DatabaseManager.DatabaseManager;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.Model.Pub;
import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOMatch extends DAOAbstract implements DatabaseConstants {

    public DAOMatch(ContentResolver contentResolver) {
        super(contentResolver);
    }

    public Match getLastMatch() {

        Cursor cursor;

        try {
            cursor = DatabaseManager.getInstance().getDataBase().rawQuery("SELECT MAX(" + MATCH_ID + ") AS MAX FROM " + TABLE_MATCH, null);
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

        Uri uriAdd = contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_MATCH, values);
        String lastPathSegment = uriAdd.getLastPathSegment();
        if (lastPathSegment != null) {
            rowID = Long.valueOf(lastPathSegment);
        }

        ArrayList<Pub> pubs = matchObj.getPubs();
        Iterator<Pub> it = pubs.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(MP_MATCH, rowID);
            values_tp.put(MP_PUB, it.next().getId());
            contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB, values_tp);
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Match matchObj = new Match();
        Cursor cursor;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH, id);
        cursor = contentResolver.query(uri, new String[] { MATCH_ID, MATCH_DATE, MATCH_LOCATION, MATCH_REFEREE, MATCH_TEAM1, MATCH_TEAM2}, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                prepareGetData(matchObj, cursor);
            } while (cursor.moveToNext());
        }

        return matchObj;
    }

    @Override
    public int update(Entity entity) {
        Match matchObj = (Match) entity;
        int count = -1;
        ContentValues values = prepareAddData(matchObj);


        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB, matchObj.getId());
        contentResolver.delete(uri, null, null);

        uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH, matchObj.getId());
        count = contentResolver.update(uri, values, null, null);

        ArrayList<Pub> pubs = matchObj.getPubs();
        Iterator<Pub> it = pubs.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(MP_MATCH, matchObj.getId());
            values_tp.put(MP_PUB, it.next().getId());
            contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB, values_tp);
        }

        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB, id);
        contentResolver.delete(uri, null, null);

        uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH, id);
        count = contentResolver.delete(uri, null, null);

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
        DAOPub daoPub = new DAOPub(contentResolver);

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB, matchObj.getId());
        cursorTmp = contentResolver.query(uri, new String[] { MP_PUB}, null, null, null, null);

        cursorTmp.moveToFirst();
        if (!cursorTmp.isAfterLast()) {
            do {
                pubs.add((Pub)daoPub.get(cursorTmp.getInt(0)));
            } while (cursorTmp.moveToNext());
        }

        DAOTeam daoTeam = new DAOTeam(contentResolver);

        matchObj.setDate(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(MATCH_DATE))));
        matchObj.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(MATCH_LOCATION)));
        matchObj.setReferee(cursor.getString(cursor.getColumnIndexOrThrow(MATCH_REFEREE)));
        matchObj.setTeam1((Team)daoTeam.get(cursor.getInt(cursor.getColumnIndexOrThrow(MATCH_TEAM1))));
        matchObj.setTeam2((Team)daoTeam.get(cursor.getInt(cursor.getColumnIndexOrThrow(MATCH_TEAM2))));
        matchObj.setPubs(pubs);
    }
}
