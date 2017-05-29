package alteirac.srmanager.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alteirac.srmanager.Activity.MainActivity;
import alteirac.srmanager.Adapter.NewsSlidePagerAdapter;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;
/**
 * Created by Guillaume on 05/05/2017.
 */

public class NewsSlideFragment extends Fragment {

    private Map<Integer, News> news;


    public static NewsSlideFragment newInstance(int news_id) {

        NewsSlideFragment personalFeedFragment = new NewsSlideFragment();

        Bundle args = new Bundle();
        args.putInt("news_id", news_id);

        personalFeedFragment.setArguments(args);

        return personalFeedFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup v = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_news, container, false);

        final ImageButton rightNav = (ImageButton) v.findViewById(R.id.right_nav);
        final ImageButton leftNav = (ImageButton) v.findViewById(R.id.left_nav);


        final int news_id = getArguments().getInt("news_id");
        news = NewsSlidePagerAdapter.map;

        if (news != null && news.size() > news_id) {

            TextView textTitle = (TextView) v.findViewById(R.id.NewsTitle);
            TextView textDesc = (TextView) v.findViewById(R.id.NewsDesc);

            textTitle.setText(news.get(news_id).getTitle());
            textDesc.setText(news.get(news_id).getDescription());
        }

        rightNav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewPager mPager = ((MainActivity)getActivity()).getViewPager();
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }

        });

        leftNav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ViewPager mPager = ((MainActivity)getActivity()).getViewPager();
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }

        });

        return v;
    }
}
