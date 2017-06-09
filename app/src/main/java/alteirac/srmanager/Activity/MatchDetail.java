package alteirac.srmanager.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import alteirac.srmanager.DatabaseManager.DAO.DAOMatch;
import alteirac.srmanager.Model.Match;
import alteirac.srmanager.R;

public class MatchDetail extends AppCompatActivity {

    private int match_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_match_detail);

        final Intent intent = getIntent();
        match_id = intent.getIntExtra("match_id",0);

        if (match_id != 0) {

            Match match = (Match)new DAOMatch(getContentResolver()).get(match_id);


            ListView list_view_players_team1 = (ListView) findViewById(R.id.players_team1);
            ListView list_view_players_team2 = (ListView) findViewById(R.id.players_team2);

            ArrayList<String> players_team1 = match.getTeam1().getPlayers();

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MatchDetail.this,
                    android.R.layout.simple_list_item_1, players_team1);
            list_view_players_team1.setAdapter(adapter);

            ArrayList<String> players_team2 = match.getTeam2().getPlayers();

            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(MatchDetail.this,
                    android.R.layout.simple_list_item_1, players_team2);
            list_view_players_team2.setAdapter(adapter2);


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
        Intent intent = new Intent();
        intent.putExtra("match_id", match_id);
        MatchDetail.this.setResult(1, intent);
        MatchDetail.this.finish();
    }
}
