package alteirac.srmanager.DatabaseManager;

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





}
