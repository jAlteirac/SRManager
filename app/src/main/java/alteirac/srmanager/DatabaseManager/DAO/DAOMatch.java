package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.Model.Entity;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOMatch extends DAOAbstract implements DatabaseConstants {


    public DAOMatch(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long add(Entity entity) {
        return 0;
    }

    @Override
    public Entity get(int id) {
        return null;
    }

    @Override
    public int update(Entity entity) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    protected ContentValues prepareAddData(Entity entityObj) {
        return null;
    }

    @Override
    protected void prepareGetData(Entity entityObj, Cursor cursor) {

    }
}
