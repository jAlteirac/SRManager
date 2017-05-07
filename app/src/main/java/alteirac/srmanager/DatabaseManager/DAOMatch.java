package alteirac.srmanager.DatabaseManager;

import android.database.sqlite.SQLiteDatabase;

import alteirac.srmanager.Model.Match;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOMatch implements DatabaseConstants {

    private SQLiteDatabase db;

    public DAOMatch(SQLiteDatabase db) {
        this.db = db;
    }

    public void addMatch(Match match) {

    }

    public Match getMatch(int id) {

    }

    public void updateMatch(Match match) {

    }

    public void deleteMatch(int id) {

    }
}
