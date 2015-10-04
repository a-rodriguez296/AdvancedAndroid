package arf.com.everpobre.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.db.DBHelper;

import static arf.com.everpobre.model.db.DBConstants.*;
import static arf.com.everpobre.model.db.DBHelper.*;

/**
 * Created by arodriguez on 9/30/15.
 */
public class NotebookDAO implements DaoPersistable<Notebook>{


    private final WeakReference<Context> context;
    public static String[] allColumns = {
            KEY_NOTEBOOK_ID,
            KEY_NOTEBOOK_NAME,
            KEY_NOTEBOOK_CREATION_DATE,
            KEY_NOTEBOOK_MODIFICATION_DATE
    };

    public NotebookDAO(@NonNull Context context) {
        this.context = new WeakReference<>(context);

    }

    @Override
    public long insert(@NonNull Notebook data) {

        if (data == null){
            return INVALID_ID;
        }

        final DBHelper dbHelper = DBHelper.getInstance(context.get());

        //Esto es una conexión a la DB.
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();
        long id = INVALID_ID;

        try{
            id = db.insert(TABLE_NOTEBOOK,null, getContentValues(data));
            //data.setId(id);
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            dbHelper.close();
        }


        return id;

    }

    @Override
    public void update(long id, @NonNull Notebook data) {

        /*Para insert y update
        Consigo una conexión, db = dbHelper.getWritableDatabase();
        Y hago un insert/update*/

        if (data == null){
            return ;
        }

        final DBHelper dbHelper = DBHelper.getInstance(context.get());

        //Esto es una conexión a la DB.
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();


        try {
            db.update(TABLE_NOTEBOOK, getContentValues(data), KEY_NOTEBOOK_ID +"=?", new String[]{""+ id});
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
            dbHelper.close();
        }

    }

    @Override
    public void delete(long id) {

        final DBHelper dbHelper = DBHelper.getInstance(context.get());

        //Esto es una conexión a la DB.
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        if (id == INVALID_ID){
            db.delete(TABLE_NOTEBOOK, null, null);
        }
        else{
            db.delete(TABLE_NOTEBOOK, KEY_NOTEBOOK_ID + "=?", new String[]{"" + id});
        }


        db.close();
    }

    @Override
    public void delete(@NonNull Notebook data) {

        if (data!= null){
            long id = data.getId();
            delete(id);
        }
    }

    @Override
    public void deleteAll() {
        delete(INVALID_ID);
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        //Trae todos los recuros

        final DBHelper dbHelper = DBHelper.getInstance(context.get());

        //Esto es una conexión a la DB.
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        Cursor cursor = db.query(TABLE_NOTEBOOK, allColumns, null, null, null, null, KEY_NOTEBOOK_ID);

        return cursor;
    }

    @Override
    public Notebook query(long id) {
        Notebook notebook = null;
        final DBHelper dbHelper = DBHelper.getInstance(context.get());

        //Esto es una conexión a la DB.
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        final String whereClause = KEY_NOTEBOOK_ID + "="+ id;

        Cursor cursor = db.query(TABLE_NOTEBOOK, allColumns, whereClause, null, null, null, KEY_NOTEBOOK_ID);

        if (cursor!=null){
            if (cursor.getCount()>0){
                /*El cursor arranca su posición antes del primer elemento.
                Por eso hay que hacer move to first*/
                cursor.moveToFirst();

                notebook = notebookFromCursor(cursor);
            }
        }

        cursor.close();
        db.close();

        return notebook;
    }

    @NonNull
    public static Notebook notebookFromCursor(Cursor cursor) {
        Notebook notebook;
        notebook= new Notebook(cursor.getString(cursor.getColumnIndex(KEY_NOTEBOOK_NAME)));
        notebook.setId(cursor.getLong(cursor.getColumnIndex(KEY_NOTEBOOK_ID)));

        long creationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTEBOOK_CREATION_DATE));
        long modificationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTE_MODIFICATION_DATE));

        notebook.setCreationgDate(DBHelper.convertLongToDate(creationDate));
        notebook.setModificationDate(DBHelper.convertLongToDate(modificationDate));
        return notebook;
    }

    public static ContentValues getContentValues(@NonNull Notebook notebook) {
        ContentValues content = new ContentValues();
        content.put(KEY_NOTEBOOK_NAME, notebook.getName());
        content.put(KEY_NOTEBOOK_CREATION_DATE, DBHelper.convertDateToLong(notebook.getCreationgDate()));
        content.put(KEY_NOTEBOOK_MODIFICATION_DATE, DBHelper.convertDateToLong(notebook.getModificationDate()));

        return content;
    }
}
