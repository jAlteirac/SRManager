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
    //Team 1 ?
    //Team 2 ?
    //pubs ?

    //TABLE NEWS
    String TABLE_NEWS = "News";
    String NEWS_ID = "id";
    String NEWS_TITLE = "title";
    String NEWS_DATE = "date";
    String NEWS_SHORT_DESC = "short_description";
    String NEWS_LONG_DESC = "long_description";
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
    //players ?
    String TEAM_IMAGE = "image";






}
