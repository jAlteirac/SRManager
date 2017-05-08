package alteirac.srmanager.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import alteirac.srmanager.Adapter.NewsSlidePagerAdapter;
import alteirac.srmanager.DatabaseManager.DAO.DAONews;
import alteirac.srmanager.DatabaseManager.DatabaseManager;
import alteirac.srmanager.Fragment.NewsSlideFragment;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;

public class MainActivity extends AppCompatActivity {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    public static Map<Integer, News> mapNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
