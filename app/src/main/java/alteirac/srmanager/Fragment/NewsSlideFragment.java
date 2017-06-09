package alteirac.srmanager.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import alteirac.srmanager.Activity.MainActivity;
import alteirac.srmanager.Activity.NewsDetail;
import alteirac.srmanager.Adapter.NewsSlidePagerAdapter;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;
/**
 * Created by Jean on 05/05/2017.
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

        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.fragment_slide_news, container, false);

        final ImageButton rightNav = (ImageButton) v.findViewById(R.id.right_nav);
        final ImageButton leftNav = (ImageButton) v.findViewById(R.id.left_nav);
        final LinearLayout linearNews = (LinearLayout) v.findViewById(R.id.linear_news);


        final int news_id = getArguments().getInt("news_id");
        news = NewsSlidePagerAdapter.map;

        if (news.size() == news_id+1) {
            rightNav.setVisibility(View.INVISIBLE);
        }else {
            rightNav.setVisibility(View.VISIBLE);
        }

        if(news_id == 0) {
            leftNav.setVisibility(View.INVISIBLE);
        }else {
            leftNav.setVisibility(View.VISIBLE);
        }

        if (news != null) {

            TextView textTitle = (TextView) v.findViewById(R.id.NewsTitle);
            TextView textDesc = (TextView) v.findViewById(R.id.NewsDesc);
            ImageView imageView = (ImageView) v.findViewById(R.id.image_news);

            byte [] imageByte = news.get(news_id).getByteImage();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            imageView.setImageBitmap(bitmap);

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

        linearNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewsDetail.class);
                intent.putExtra("news_id", news_id+1);
                startActivityForResult(intent, 1);
            }
        });


        return v;
    }
}
