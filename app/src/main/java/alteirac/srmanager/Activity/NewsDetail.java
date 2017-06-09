package alteirac.srmanager.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import alteirac.srmanager.DatabaseManager.DAO.DAONews;
import alteirac.srmanager.Model.News;
import alteirac.srmanager.R;

public class NewsDetail extends AppCompatActivity {

    private int news_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);

        final Intent intent = getIntent();
        news_id = intent.getIntExtra("news_id",0);

        if (news_id != 0) {

            News news = (News)new DAONews(getContentResolver()).get(news_id);


            TextView newsTitle = (TextView) findViewById(R.id.news_title);
            TextView newsDate = (TextView) findViewById(R.id.news_date);
            TextView newsDesc = (TextView) findViewById(R.id.news_desc);
            ImageView newsImage = (ImageView) findViewById(R.id.news_image);


            newsTitle.setText(news.getTitle());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            newsDate.setText(dateFormat.format(news.getDate()));
            newsDesc.setText(news.getDescription());
            byte [] imageByte = news.getByteImage();
            Bitmap bitmap= BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            newsImage.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("news_id", news_id);
        NewsDetail.this.setResult(1, intent);
        NewsDetail.this.finish();
    }
}
