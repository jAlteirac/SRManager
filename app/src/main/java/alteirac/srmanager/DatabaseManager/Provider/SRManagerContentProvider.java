package alteirac.srmanager.DatabaseManager.Provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import alteirac.srmanager.DatabaseManager.DatabaseConstants;
import alteirac.srmanager.DatabaseManager.DatabaseManager;

/**
 * Created by Jean on 06/06/2017.
 */

public class SRManagerContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher;
    SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_NEWS, DatabaseConstants.ALL_NEWS);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_NEWS, DatabaseConstants.ONE_NEWS);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_MATCH, DatabaseConstants.ALL_MATCH);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_MATCH, DatabaseConstants.ONE_MATCH);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_PUB, DatabaseConstants.ALL_PUB);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_PUB, DatabaseConstants.ONE_PUB);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_TEAM, DatabaseConstants.ALL_TEAM);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_TEAM, DatabaseConstants.ONE_TEAM);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_MATCH_PUB, DatabaseConstants.ALL_MATCH_PUB);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_MATCH_PUB, DatabaseConstants.ONE_MATCH_PUB);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ALL_TEAM_PLAYER, DatabaseConstants.ALL_TEAM_PLAYER);
        uriMatcher.addURI(DatabaseConstants.AUTHORITY, DatabaseConstants.PATH_ONE_TEAM_PLAYER, DatabaseConstants.ONE_TEAM_PLAYER);
    }


    @Override
    public boolean onCreate() {
        db = DatabaseManager.getInstance(getContext()).getDataBase();
        return (db == null)? false:true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String clauseWhere = null;
        String table = null;
        Cursor c = null;

        switch (uriMatcher.match(uri)) {
            case DatabaseConstants.ALL_NEWS:
                table = DatabaseConstants.TABLE_NEWS;
                break;
            case DatabaseConstants.ONE_NEWS:
                table = DatabaseConstants.TABLE_NEWS;
                clauseWhere = DatabaseConstants.NEWS_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH:
                table = DatabaseConstants.TABLE_MATCH;
                clauseWhere = DatabaseConstants.MATCH_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_PUB:
                table = DatabaseConstants.TABLE_PUB;
                clauseWhere = DatabaseConstants.PUB_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM:
                table = DatabaseConstants.TABLE_TEAM;
                clauseWhere = DatabaseConstants.TEAM_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH_PUB:
                table = DatabaseConstants.TABLE_MP;
                clauseWhere = DatabaseConstants.MP_MATCH+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM_PLAYER:
                table = DatabaseConstants.TABLE_TP;
                clauseWhere = DatabaseConstants.TP_TEAM+" = "+uri.getPathSegments().get(1);
                break;
        }

        try {
            c = db.query(table, projection, clauseWhere, selectionArgs, null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        String type = null;
        switch (uriMatcher.match(uri)) {
            case DatabaseConstants.ALL_NEWS:
                type = new String ("vnd.android.cursor.dir/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_NEWS);
                break;
            case DatabaseConstants.ONE_NEWS:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_NEWS);
                break;
            case DatabaseConstants.ONE_MATCH:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_MATCH);
                break;
            case DatabaseConstants.ONE_PUB:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_PUB);
                break;
            case DatabaseConstants.ONE_TEAM:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_TEAM);
                break;
            case DatabaseConstants.ONE_MATCH_PUB:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_MP);
                break;
            case DatabaseConstants.ONE_TEAM_PLAYER:
                type = new String ("vnd.android.cursor.item/vnd."+DatabaseConstants.AUTHORITY+"."+DatabaseConstants.TABLE_TP);
                break;
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String table = null;
        Uri content_uri = null;
        Uri newUri = null;

        switch (uriMatcher.match(uri)) {
            case DatabaseConstants.ALL_NEWS:
                table = DatabaseConstants.TABLE_NEWS;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_NEWS;
                break;
            case DatabaseConstants.ALL_MATCH:
                table = DatabaseConstants.TABLE_MATCH;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_MATCH;
                break;
            case DatabaseConstants.ALL_PUB:
                table = DatabaseConstants.TABLE_PUB;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_PUB;
                break;
            case DatabaseConstants.ALL_TEAM:
                table = DatabaseConstants.TABLE_TEAM;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_TEAM;
                break;
            case DatabaseConstants.ALL_MATCH_PUB:
                table = DatabaseConstants.TABLE_MP;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_MATCH_PUB;
                break;
            case DatabaseConstants.ALL_TEAM_PLAYER:
                table = DatabaseConstants.TABLE_TP;
                content_uri = DatabaseConstants.CONTENT_URI_ALL_TEAM_PLAYER;
                break;
        }

        try {
            long rowID = db.insert(table, null, values);
            newUri = ContentUris.withAppendedId(content_uri, rowID);
            getContext().getContentResolver().notifyChange(newUri, null);

        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return newUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        String clauseWhere = null;
        String table = null;
        int count = -1;

        switch (uriMatcher.match(uri)) {
            case DatabaseConstants.ONE_NEWS:
                table = DatabaseConstants.TABLE_NEWS;
                clauseWhere = DatabaseConstants.NEWS_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH:
                table = DatabaseConstants.TABLE_MATCH;
                clauseWhere = DatabaseConstants.MATCH_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_PUB:
                table = DatabaseConstants.TABLE_PUB;
                clauseWhere = DatabaseConstants.PUB_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM:
                table = DatabaseConstants.TABLE_TEAM;
                clauseWhere = DatabaseConstants.TEAM_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH_PUB:
                table = DatabaseConstants.TABLE_MP;
                clauseWhere = DatabaseConstants.MP_MATCH+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM_PLAYER:
                table = DatabaseConstants.TABLE_TP;
                clauseWhere = DatabaseConstants.TP_TEAM+" = "+uri.getPathSegments().get(1);
                break;
        }
        try {
            count = db.delete(table, clauseWhere, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);

        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        String clauseWhere = null;
        String table = null;
        int count = -1;

        switch (uriMatcher.match(uri)) {
            case DatabaseConstants.ONE_NEWS:
                table = DatabaseConstants.TABLE_NEWS;
                clauseWhere = DatabaseConstants.NEWS_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH:
                table = DatabaseConstants.TABLE_MATCH;
                clauseWhere = DatabaseConstants.MATCH_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_PUB:
                table = DatabaseConstants.TABLE_PUB;
                clauseWhere = DatabaseConstants.PUB_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM:
                table = DatabaseConstants.TABLE_TEAM;
                clauseWhere = DatabaseConstants.TEAM_ID+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_MATCH_PUB:
                table = DatabaseConstants.TABLE_MP;
                clauseWhere = DatabaseConstants.MP_MATCH+" = "+uri.getPathSegments().get(1);
                break;
            case DatabaseConstants.ONE_TEAM_PLAYER:
                table = DatabaseConstants.TABLE_TP;
                clauseWhere = DatabaseConstants.TP_TEAM+" = "+uri.getPathSegments().get(1);
                break;
        }
        try {
            count = db.update(table, values, clauseWhere, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);

        }catch (Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }

        return count;
    }
}