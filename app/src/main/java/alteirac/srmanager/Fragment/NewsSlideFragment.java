package alteirac.srmanager.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import alteirac.srmanager.Activity.MainActivity;
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

        final int news_id = getArguments().getInt("news_id");
        Log.d("DEBUG","news_id : "+news_id);

        TextView textTitle = (TextView) v.findViewById(R.id.NewsTitle);
        TextView textDesc = (TextView) v.findViewById(R.id.NewsDesc);

        textTitle.setText(news.get(news_id).getTitle());
        textDesc.setText(news.get(news_id).getDescription());

        return v;
    }

}
