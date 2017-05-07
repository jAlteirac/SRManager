package alteirac.srmanager.DatabaseManager;

import android.database.sqlite.SQLiteDatabase;

import alteirac.srmanager.Model.Pub;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOPub implements DatabaseConstants {

    private SQLiteDatabase db;

    public DAOPub (SQLiteDatabase db) {
        this.db = db;
    }

    public void addPub(Pub pub) {

    }

    public Pub getPub(int id) {

    }

    public void updatePub(Pub pub) {

    }

    public void deletePub(int id) {

    }
}
