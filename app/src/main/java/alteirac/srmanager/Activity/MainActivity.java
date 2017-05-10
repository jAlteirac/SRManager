package alteirac.srmanager.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import alteirac.srmanager.Adapter.NewsSlidePagerAdapter;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;
import alteirac.srmanager.WebService.DBLoader;

public class MainActivity extends AppCompatActivity {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public static Map<Integer, News> mapNews;
    public static ArrayList<News> listNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void[] params) {

                DBLoader dbLoader = new DBLoader();
                listNews = dbLoader.exec();

                mapNews = new HashMap<Integer, News>();

                if (listNews != null) {
                    listNews.add(new News("Super titre de news !", "Voilà c'est une news test on prend du temps."));
                    Iterator<News> it = listNews.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        News news = it.next();
                        mapNews.put(i, news);
                        i++;
                    }
                }

                return null;

            }

            @Override
            protected void onPostExecute(Void result) {

                mPager = (ViewPager) findViewById(R.id.pager);
                mPagerAdapter = new NewsSlidePagerAdapter(getSupportFragmentManager(), listNews.size());
                mPager.setAdapter(mPagerAdapter);
            }

        }.execute();


    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


}
