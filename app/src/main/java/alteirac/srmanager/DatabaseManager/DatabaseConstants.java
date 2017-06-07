package alteirac.srmanager.DatabaseManager;

import android.net.Uri;

/**
 * Created by Jean on 07/05/2017.
 */

public interface DatabaseConstants {

    String DB_NAME = "SRMANAGER";
    int DB_VERSION = 1;

    // TABLE MATCH
    String TABLE_MATCH = "Match";
    String MATCH_ID = "id";
    String MATCH_DATE = "date";
    String MATCH_LOCATION = "location";
    String MATCH_REFEREE = "referee";
    String MATCH_TEAM1 = "id_team1";
    String MATCH_TEAM2 = "id_team2";

    //TABLE NEWS
    String TABLE_NEWS = "News";
    String NEWS_ID = "id";
    String NEWS_TITLE = "title";
    String NEWS_DATE = "date";
    String NEWS_DESC = "description";
    String NEWS_IMAGE = "image";

    //TABLE PUB
    String TABLE_PUB = "Pub";
    String PUB_ID = "id";
    String PUB_NAME = "name";
    String PUB_LAT = "lat";
    String PUB_LNG = "lng";
    String PUB_IMAGE = "image";

    //TABLE TEAM
    String TABLE_TEAM = "Team";
    String TEAM_ID = "id";
    String TEAM_NAME = "name";
    String TEAM_IMAGE = "image";

    //TABLE MATCH_PUB (MP)
    String TABLE_MP = "MatchPub";
    String MP_MATCH = "id_match";
    String MP_PUB = "id_pub";

    //TABLE TEAM_PLAYER (TP)
    String TABLE_TP = "TeamPlayer";
    String TP_TEAM = "id_team";
    String TP_PLAYER = "player_name";


    //URI
    String AUTHORITY = "alteirac.srmanager.provider";
    String PATH_ALL_NEWS = "news";
    String PATH_ONE_NEWS = "news/#";
    Uri CONTENT_URI_ALL_NEWS =  Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_NEWS);
    Uri CONTENT_URI_ONE_NEWS =  Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_NEWS);
    int ALL_NEWS = 1;
    int ONE_NEWS = 2;

    String PATH_ALL_MATCH = "match";
    String PATH_ONE_MATCH = "match/#";
    Uri CONTENT_URI_ALL_MATCH = Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_MATCH);
    Uri CONTENT_URI_ONE_MATCH = Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_MATCH);
    int ALL_MATCH = 3;
    int ONE_MATCH = 4;

    String PATH_ALL_PUB = "pub";
    String PATH_ONE_PUB = "pub/#";
    Uri CONTENT_URI_ALL_PUB = Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_PUB);
    Uri CONTENT_URI_ONE_PUB = Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_PUB);
    int ALL_PUB = 5;
    int ONE_PUB = 6;

    String PATH_ALL_TEAM = "team";
    String PATH_ONE_TEAM = "team/#";
    Uri CONTENT_URI_ALL_TEAM = Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_TEAM);
    Uri CONTENT_URI_ONE_TEAM = Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_TEAM);
    int ALL_TEAM = 7;
    int ONE_TEAM = 8;

    String PATH_ALL_MATCH_PUB = "matchpub";
    String PATH_ONE_MATCH_PUB = "matchpub/#";
    Uri CONTENT_URI_ALL_MATCH_PUB = Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_MATCH_PUB);
    Uri CONTENT_URI_ONE_MATCH_PUB = Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_MATCH_PUB);
    int ALL_MATCH_PUB = 9;
    int ONE_MATCH_PUB = 10;

    String PATH_ALL_TEAM_PLAYER = "teamplayer";
    String PATH_ONE_TEAM_PLAYER = "teamplayer/#";
    Uri CONTENT_URI_ALL_TEAM_PLAYER = Uri.parse("content://"+AUTHORITY+"/"+PATH_ALL_TEAM_PLAYER);
    Uri CONTENT_URI_ONE_TEAM_PLAYER = Uri.parse("content://"+AUTHORITY+"/"+PATH_ONE_TEAM_PLAYER);
    int ALL_TEAM_PLAYER = 11;
    int ONE_TEAM_PLAYER = 12;

}