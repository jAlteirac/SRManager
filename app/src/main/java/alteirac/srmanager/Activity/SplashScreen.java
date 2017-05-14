package alteirac.srmanager.Activity;

import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import alteirac.srmanager.DatabaseManager.DAO.DAOMatch;
import alteirac.srmanager.DatabaseManager.DAO.DAONews;
import alteirac.srmanager.DatabaseManager.DAO.DAOPub;
import alteirac.srmanager.DatabaseManager.DAO.DAOTeam;
import alteirac.srmanager.DatabaseManager.DatabaseManager;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.Model.Pub;
import alteirac.srmanager.Model.Team;
import alteirac.srmanager.R;
import alteirac.srmanager.WebService.DBLoader;

/**
 * Created by Jean on 14/05/2017.
 */

public class SplashScreen extends Activity {

    SQLiteDatabase db;
    DAONews daoNews;
    DAOMatch daoMatch;
    DAOTeam daoTeam;
    DAOPub daoPub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        DatabaseManager dm = new DatabaseManager(this);
        dm.DeleteAll();
        db = dm.getDataBase();
        new WSCall().execute();

    }

    private class WSCall extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<News> listNews;
            Match match;

            DBLoader dbLoader = new DBLoader();
            listNews = dbLoader.getAllNews();


            if (listNews != null) {
                daoNews = new DAONews(db);
                Iterator<News> it = listNews.iterator();
                while (it.hasNext()) {
                    News news = it.next();
                    daoNews.add(news);
                }
            }

            match = dbLoader.getLastMatch();
            if (match != null) {
                daoMatch = new DAOMatch(db);
                daoTeam = new DAOTeam(db);
                daoPub = new DAOPub(db);

                Team team1 = match.getTeam1();
                long id = daoTeam.add(team1);
                if(id != -1) {
                    team1.setId((int)id);
                    match.setTeam1(team1);
                }
                Team team2 = match.getTeam2();
                id = daoTeam.add(team2);
                if(id != -1) {
                    team2.setId((int)id);
                    match.setTeam2(team2);
                }
                ArrayList<Pub> pubs = match.getPubs();
                ArrayList<Pub> newPubs = new ArrayList<>();
                Iterator<Pub> it = pubs.iterator();
                while (it.hasNext()) {
                    Pub pub = it.next();
                    id = daoPub.add(pub);
                    if (id != -1) {
                        pub.setId((int)id);
                        newPubs.add(pub);
                    }
                }
                match.setPubs(newPubs);
                long ret = daoMatch.add(match);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();
        }


    }
}
