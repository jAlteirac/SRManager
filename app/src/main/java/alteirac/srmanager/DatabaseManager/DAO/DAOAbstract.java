package alteirac.srmanager.DatabaseManager.DAO;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import alteirac.srmanager.Model.Entity;

/**
 * Created by Jean on 07/05/2017.
 */

public abstract class DAOAbstract {

    protected ContentResolver contentResolver;

    public DAOAbstract(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public abstract long add(Entity entity);

    public abstract Entity get(int id);

    public abstract int update(Entity entity);

    public abstract int delete(int id);

    protected abstract ContentValues prepareAddData(Entity entityObj);

    protected abstract void prepareGetData(Entity entityObj, Cursor cursor);
}
