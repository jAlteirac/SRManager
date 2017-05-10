package alteirac.srmanager.Activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


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

        mapNews = new HashMap<Integer, News>();
        listNews = new ArrayList<>();

        DBLoader dbLoader = new DBLoader(this);
        dbLoader.loadInBackground();
        
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


        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new NewsSlidePagerAdapter(getSupportFragmentManager());
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


}
