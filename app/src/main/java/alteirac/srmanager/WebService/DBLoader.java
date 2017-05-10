package alteirac.srmanager.WebService;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

import alteirac.srmanager.Activity.MainActivity;
import alteirac.srmanager.Model.News;

/**
 * Created by Jean on 10/05/2017.
 */

public class DBLoader {

    private final String URL = "http://193.250.94.183:90/my-rest-api/api/news";
    
    public DBLoader() {

    }

    public ArrayList<News> exec()
    {
        try {
            ArrayList<News> listNews = new ArrayList<>();
            URL url = new URL(URL);
            Gson gson = new Gson();

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    //String json = IOUtils.toString(inputStream, "UTF-8");
                    //Log.e("WebService", json);
                    //listNews = gson.fromJson(json, new TypeToken<ArrayList<News>>(){}.getType());

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
