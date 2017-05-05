package alteirac.srmanager.Adapter;

/**
 * Created by Guillaume on 05/05/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import alteirac.srmanager.Fragment.NewsSlideFragment;

/**
 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
 * sequence.
 */
public class NewsSlidePagerAdapter extends FragmentStatePagerAdapter {


    private static final int NUMBER_OF_TABS = 4;


    public NewsSlidePagerAdapter(FragmentManager fm) {
        super(fm);
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
