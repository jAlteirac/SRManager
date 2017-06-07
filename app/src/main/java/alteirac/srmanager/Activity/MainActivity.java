package alteirac.srmanager.Activity;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import alteirac.srmanager.Adapter.NewsSlidePagerAdapter;
import alteirac.srmanager.DatabaseManager.DAO.DAOMatch;
import alteirac.srmanager.DatabaseManager.DAO.DAONews;
import alteirac.srmanager.DatabaseManager.DatabaseManager;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;
import alteirac.srmanager.WebService.DBLoader;

public class MainActivity extends AppCompatActivity {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private Match match;
    private ArrayList<News> listNews;
    private DAONews daoNews;
    private DAOMatch daoMatch;

    ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentResolver = getContentResolver();

        DAONews daoNews = new DAONews(contentResolver);
        listNews = daoNews.getAllNews();


        daoMatch = new DAOMatch(contentResolver);
        match = daoMatch.getLastMatch();

        if (match != null) {
            TextView affichMatch = (TextView) findViewById(R.id.text_next_match_eq1);
            TextView affichMatch2 = (TextView) findViewById(R.id.text_next_match_eq2);
            TextView affichMatchDate = (TextView) findViewById(R.id.text_next_match_date);

            affichMatch.setText(match.getTeam1().getName());
            affichMatch2.setText(match.getTeam2().getName());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            affichMatchDate.setText(sdf.format(match.getDate()));
        }


        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new NewsSlidePagerAdapter(getSupportFragmentManager(), listNews.size(), listNews);
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public ViewPager getViewPager() {
        if (mPager == null) {
            mPager = (ViewPager) findViewById(R.id.pager);
        }
        return mPager;
    }

}
