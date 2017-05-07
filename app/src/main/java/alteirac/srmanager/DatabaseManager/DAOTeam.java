package alteirac.srmanager.DatabaseManager;

import android.database.sqlite.SQLiteDatabase;

import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 07/05/2017.
 */

public class DAOTeam implements DatabaseConstants {

    private SQLiteDatabase db;

    public DAOTeam(SQLiteDatabase db) {
        this.db = db;
    }

    public void addTeam(Team team) {

    }

    public Team getTeam(int id) {

    }

    public void updateTeam(Team team) {

    }

    public void deleteTeam(int id) {

    }
}
