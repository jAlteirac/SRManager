package alteirac.srmanager.WebService;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URL;

import alteirac.srmanager.Activity.MainActivity;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.Model.Pub;
import alteirac.srmanager.Model.Team;

/**
 * Created by Jean on 10/05/2017.
 */

public class DBLoader {

    private final String URL_allNews = "http://86.234.102.163:90/srmanager/news";
    private final String URL_lastMatch = "http://86.234.102.163:90/srmanager/match";
    Gson gson;
    
    public DBLoader() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    public Match getLastMatch() {
        try {
            Match match = new Match();

            URL url = new URL(URL_lastMatch);


            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                    JsonParser parser = new JsonParser();
                    JsonObject jObjectAll = parser.parse(reader).getAsJsonObject();

                    match.setDate((Date)new SimpleDateFormat("yyyy-MM-dd").parse(jObjectAll.get("date").getAsString()));
                    match.setLocation(jObjectAll.get("location").getAsString());
                    match.setReferee(jObjectAll.get("referee").getAsString());

                    //team 1
                    Team team1 = new Team();
                    ArrayList<String> players = new ArrayList<>();
                    JsonObject jObjectTeam1 = jObjectAll.get("team1").getAsJsonObject();
                    team1.setName(jObjectTeam1.get("name").getAsString());
                    JsonArray jArrayTeam1 = jObjectTeam1.get("players").getAsJsonArray();
                    for (int i = 0; i < jArrayTeam1.size(); i++) {
                        JsonObject jObj = (JsonObject)jArrayTeam1.get(i);
                        players.add(jObj.get("name").getAsString());
                    }
                    team1.setPlayers(players);
                    match.setTeam1(team1);

                    //team 2
                    Team team2 = new Team();
                    ArrayList<String> players2 = new ArrayList<>();
                    JsonObject jObjectTeam2 = jObjectAll.get("team2").getAsJsonObject();
                    team2.setName(jObjectTeam2.get("name").getAsString());
                    JsonArray jArrayTeam2 = jObjectTeam2.get("players").getAsJsonArray();
                    for (int i = 0; i < jArrayTeam2.size(); i++) {
                        JsonObject jObj = (JsonObject)jArrayTeam2.get(i);
                        players2.add(jObj.get("name").getAsString());
                    }
                    team2.setPlayers(players2);
                    match.setTeam2(team2);

                    //pubs
                    ArrayList<Pub> pubs = new ArrayList<>();
                    JsonArray jArray = (JsonArray)jObjectAll.get("pubs");
                    for(JsonElement obj : jArray )
                    {
                        Pub pub = gson.fromJson(obj , Pub.class);
                        pubs.add(pub);
                    }
                    match.setPubs(pubs);

                    return match;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("WebService", "Impossible de rapatrier les données.");
        }

        return null;
    }



    public ArrayList<News> getAllNews()
    {
        try {
            ArrayList<News> listNews = new ArrayList<>();
            URL url = new URL(URL_allNews);


            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {

                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                    JsonParser parser = new JsonParser();
                    JsonArray jArray =  (JsonArray) parser.parse(reader).getAsJsonObject().get("news");
                    for(JsonElement obj : jArray )
                    {
                        News news = gson.fromJson(obj , News.class);
                        listNews.add(news);
                    }
                    return listNews;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("WebService", "Impossible de rapatrier les données.");
        }

        return null;
    }
}
