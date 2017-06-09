package alteirac.srmanager.Adapter;

/**
 * Created by Guillaume on 05/05/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import alteirac.srmanager.Fragment.NewsSlideFragment;
import alteirac.srmanager.Model.News;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class NewsSlidePagerAdapter extends FragmentStatePagerAdapter {


    private static int NUMBER_OF_TABS;
    public static HashMap<Integer, News> map;


    public NewsSlidePagerAdapter(FragmentManager fm, int size, List<News> listNews) {

        super(fm);
        NUMBER_OF_TABS = size;
        map = new HashMap<>();
        if (listNews != null) {
            Iterator<News> it = listNews.iterator();
            int i = 0;
            while (it.hasNext()) {
                News news = it.next();
                map.put(i, news);
                i++;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return NewsSlideFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUMBER_OF_TABS;
    }

}
