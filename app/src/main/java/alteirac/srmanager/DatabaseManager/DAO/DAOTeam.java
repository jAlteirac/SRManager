package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;
import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOTeam extends DAOAbstract implements DatabaseConstants {


    public DAOTeam(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long add(Entity entity) {
        Team teamObj = (Team) entity;
        long rowID = -1;
        ContentValues values = prepareAddData(teamObj);

        try {
            rowID = db.insert(TABLE_TEAM, null, values);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        ArrayList<String> players = teamObj.getPlayers();
        Iterator<String> it = players.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(TP_TEAM, rowID);
            values_tp.put(TP_PLAYER, it.next());
            try {
                db.insert(TABLE_TP, null, values_tp);
            } catch (Exception e) {
                Log.e("DB ERROR", e.toString());
                e.printStackTrace();
            }
        }

        return rowID;
    }

    @Override
    public Entity get(int id) {
        Team teamObj = new Team();
        Cursor cursor;

        try {
            cursor = db.query(TABLE_TEAM,
                    new String[] { TEAM_ID, TEAM_NAME, TEAM_IMAGE},
                    TEAM_ID + "=" + id, null, null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                do {
                    prepareGetData(teamObj, cursor);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return teamObj;
    }

    @Override
    public int update(Entity entity) {
        Team teamObj = (Team) entity;
        int count = -1;
        ContentValues values = prepareAddData(teamObj);

        String whereClause = TEAM_ID + "=?";
        String whereArgs[] = new String[] { String.valueOf(teamObj.getId()) };

        try {
            db.delete(TABLE_TP, TP_TEAM + "=" + teamObj.getId(), null);
            count = db.update(TABLE_TEAM, values, whereClause, whereArgs);
        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        ArrayList<String> players = teamObj.getPlayers();
        Iterator<String> it = players.iterator();

        while(it.hasNext()) {
            ContentValues values_tp = new ContentValues();
            values_tp.put(TP_TEAM, teamObj.getId());
            values_tp.put(TP_PLAYER, it.next());
            try {
                db.insert(TABLE_TP, null, values_tp);
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
            db.delete(TABLE_TP, TP_TEAM + "=" + id, null);
            count = db.delete(TABLE_TEAM, TEAM_ID + "=" + id, null);
        } catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

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

        try {
            cursorTmp = db.query(TABLE_TP,
                    new String[] { TP_PLAYER},
                    TP_TEAM + "=" + teamObj.getId(), null, null, null, null, null);
            cursorTmp.moveToFirst();
            if (!cursorTmp.isAfterLast()) {
                do {
                    players.add(cursorTmp.getString(0));
                } while (cursorTmp.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        teamObj.setName(cursor.getString(cursor.getColumnIndexOrThrow(TEAM_NAME)));
        teamObj.setPlayers(players);
        teamObj.setByteImage(cursor.getBlob(cursor.getColumnIndexOrThrow(TEAM_IMAGE)));
    }
}
