package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Iterator;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOTeam extends DAOAbstract implements DatabaseConstants {

    public DAOTeam(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public long add(Entity entity) {
        Team teamObj = (Team) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(teamObj);

        Uri uriAdd = contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_TEAM, values);
        String lastPathSegment = uriAdd.getLastPathSegment();
        if (lastPathSegment != null) {
            rowID = Long.valueOf(lastPathSegment);
        }

        ArrayList<String> players = teamObj.getPlayers();
        Iterator<String> it = players.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(TP_TEAM, rowID);
            values_tp.put(TP_PLAYER, it.next());
            contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER, values_tp);
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Team teamObj = new Team();
        Cursor cursor;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM, id);
        cursor = contentResolver.query(uri, new String[] {TEAM_ID, TEAM_NAME, TEAM_IMAGE}, null, null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                prepareGetData(teamObj, cursor);
            } while (cursor.moveToNext());
        }

        return teamObj;
    }

    @Override
    public int update(Entity entity) {
        Team teamObj = (Team) entity;
        int count = -1;
        ContentValues values = prepareAddData(teamObj);

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER, teamObj.getId());
        contentResolver.delete(uri, null, null);

        uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM, teamObj.getId());
        count = contentResolver.update(uri, values, null, null);


        ArrayList<String> players = teamObj.getPlayers();
        Iterator<String> it = players.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(TP_TEAM, teamObj.getId());
            values_tp.put(TP_PLAYER, it.next());
            contentResolver.insert(DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER, values_tp);
        }

        return count;
    }

    @Override
    public int delete(int id) {
        int count = -1;

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER, id);
        contentResolver.delete(uri, null, null);

        uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM, id);
        count = contentResolver.delete(uri, null, null);

        return count;
    }

    @Override
    protected ContentValues prepareAddData(Entity entityObj) {

        Team teamObj = (Team) entityObj;
        ContentValues values = new ContentValues();
        values.put(TEAM_NAME, teamObj.getName());
        values.put(TEAM_IMAGE, teamObj.getByteImage());
        return values;
    }

    @Override
    protected void prepareGetData(Entity entityObj, Cursor cursor) {
        Team teamObj = (Team) entityObj;
        teamObj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TEAM_ID)));
        Cursor cursorTmp;
        ArrayList<String> players = new ArrayList<String>();

        Uri uri = ContentUris.withAppendedId(DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER, teamObj.getId());
        cursorTmp = contentResolver.query(uri, new String[] { TP_PLAYER}, null, null, null, null);

        cursorTmp.moveToFirst();
        if (!cursorTmp.isAfterLast()) {
            do {
                players.add(cursorTmp.getString(0));
            } while (cursorTmp.moveToNext());
        }
        teamObj.setName(cursor.getString(cursor.getColumnIndexOrThrow(TEAM_NAME)));
        teamObj.setPlayers(players);
        teamObj.setByteImage(cursor.getBlob(cursor.getColumnIndexOrThrow(TEAM_IMAGE)));
    }
}
