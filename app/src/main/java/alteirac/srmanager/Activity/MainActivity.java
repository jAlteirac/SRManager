package alteirac.srmanager.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
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
import alteirac.srmanager.Model.Pub;
import alteirac.srmanager.R;
import alteirac.srmanager.WebService.DBLoader;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private Match match;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        CardView cardViewMatch = (CardView) findViewById(R.id.cardViewMatch);
        cardViewMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MatchDetail.class);
                intent.putExtra("match_id", match.getId());
                startActivityForResult(intent, 1);
            }
        });


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ContentResolver contentResolver = getContentResolver();

        DAONews daoNews = new DAONews(contentResolver);
        ArrayList<News> listNews = daoNews.getAllNews();

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new NewsSlidePagerAdapter(getSupportFragmentManager(), listNews.size(), listNews);
        mPager.setAdapter(mPagerAdapter);


        DAOMatch daoMatch = new DAOMatch(contentResolver);
        match = daoMatch.getLastMatch();

        if (match != null) {
            TextView affichMatch = (TextView) findViewById(R.id.text_next_match_eq1);
            TextView affichMatch2 = (TextView) findViewById(R.id.text_next_match_eq2);
            TextView affichMatchDate = (TextView) findViewById(R.id.text_next_match_date);
            ImageView image_eq1 = (ImageView) findViewById(R.id.image_next_match_eq1);
            ImageView image_eq2 = (ImageView) findViewById(R.id.image_next_match_eq2);

            affichMatch.setText(match.getTeam1().getName());
            affichMatch2.setText(match.getTeam2().getName());

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            affichMatchDate.setText(dateFormat.format(match.getDate()));

            byte [] imageByte = match.getTeam1().getByteImage();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            image_eq1.setImageBitmap(bitmap);

            imageByte = match.getTeam2().getByteImage();
            bitmap= BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            image_eq2.setImageBitmap(bitmap);
        }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        ArrayList<Pub> pubs = match.getPubs();
        Iterator<Pub> it = pubs.iterator();

        LatLng latLng = null;

        while (it.hasNext()) {
            Pub pub = it.next();
            Log.e("Pub: ",pub.getName());
            latLng = new LatLng(pub.getLat(), pub.getLng());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(pub.getName()));
        }
        if (latLng != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }
}
