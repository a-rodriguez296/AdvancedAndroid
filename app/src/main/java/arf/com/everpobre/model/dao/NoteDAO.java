package arf.com.everpobre.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.Date;

import arf.com.everpobre.model.Note;

import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.db.DBHelper;

import static arf.com.everpobre.model.db.DBConstants.*;

/**
 * Created by arodriguez on 9/30/15.
 */
public class NoteDAO implements DaoPersistable<Note> {


    private final WeakReference<Context> context;
    public static final String[] allColumns = {
            KEY_NOTE_ID,
            KEY_NOTE_TEXT,
            KEY_NOTE_CREATION_DATE,
            KEY_NOTE_MODIFICATION_DATE,
            KEY_NOTE_PHOTO_URL,
            KEY_NOTE_NOTEBOOK,
            KEY_NOTE_LATITUDE,
            KEY_NOTE_LONGITUDE,
            KEY_NOTE_HAS_COORDINATES,
            KEY_NOTE_ADDRESS
    };


    public NoteDAO(@NonNull Context context) {
        this.context = new WeakReference<Context>(context);
    }

    @Override
    public long insert(@NonNull Note data) {


        if (data == null) {
            return DBHelper.INVALID_ID;
        }

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;

        try {
            id = db.insert(TABLE_NOTE, null, getContentValues(data));
            // data.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            dbHelper.close();
        }

        return id;

    }

    @Override
    public void update(long id, @NonNull Note data) {
        if (data == null) {
            return;
        }

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        db.beginTransaction();

        try {
            db.update(TABLE_NOTE, getContentValues(data), KEY_NOTE_ID + "=" + id, null);
            // db.update(TABLE_NOTEBOOK, getContentValues(data), KEY_NOTE_ID + "=?", new String[] { "" + id });
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            dbHelper.close();
        }

    }

    @Override
    public void delete(long id) {

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        if (id == DBHelper.INVALID_ID) {
            db.delete(TABLE_NOTE, null, null);
        } else {
            db.delete(TABLE_NOTE, KEY_NOTE_ID + "=?", new String[]{"" + id});
        }

        db.close();
    }

    @Override
    public void delete(@NonNull Note data) {
        if (data != null) {
            delete(data.getId());
        }
    }

    @Override
    public void deleteAll() {
        delete(DBHelper.INVALID_ID);
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        Cursor cursor = db.query(TABLE_NOTE, allColumns, null, null, null, null, KEY_NOTE_ID);
        return cursor;

    }

    @Override
    public Note query(long id) {
        Note note = null;

        final DBHelper dbHelper = DBHelper.getInstance(context.get());
        SQLiteDatabase db = DBHelper.getDb(dbHelper);

        final String whereClause = KEY_NOTE_ID + "=" + id;
        Cursor cursor = db.query(TABLE_NOTE, allColumns, whereClause, null, null, null, KEY_NOTE_ID);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                Notebook notebook = new Notebook("");
                final String notebookId = cursor.getString(cursor.getColumnIndex(KEY_NOTE_NOTEBOOK));
                notebook.setId(Long.parseLong(notebookId));

                note = new Note(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TEXT)), notebook);
                note.setId(cursor.getLong(cursor.getColumnIndex(KEY_NOTE_ID)));

                long creationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTE_CREATION_DATE));
                long modificationDate = cursor.getLong(cursor.getColumnIndex(KEY_NOTE_MODIFICATION_DATE));

                note.setCreationDate(DBHelper.convertLongToDate(creationDate));
                note.setModificationDate(DBHelper.convertLongToDate(modificationDate));

                // TODO

                //Falta por añadir el resto de datos

            }
        }

        cursor.close();
        db.close();

        return note;
    }

    public static ContentValues getContentValues(Note note) {


        if (note.getCreationDate() == null) {
            note.setCreationDate(new Date());
        }

        if (note.getModificationDate() == null) {
            note.setModificationDate(new Date());
        }

        ContentValues content = new ContentValues();
        content.put(KEY_NOTE_TEXT, note.getText());
        content.put(KEY_NOTE_CREATION_DATE, DBHelper.convertDateToLong(note.getCreationDate()));
        content.put(KEY_NOTE_MODIFICATION_DATE, DBHelper.convertDateToLong(note.getModificationDate()));
        content.put(KEY_NOTE_PHOTO_URL, note.getPhotoURL());
        content.put(KEY_NOTE_NOTEBOOK, String.format("%d", note.getNotebook()));
        content.put(KEY_NOTE_LATITUDE, String.format("%f", note.getLatitude()));
        content.put(KEY_NOTE_LONGITUDE, String.format("%f", note.getLongitude()));

        Boolean hasCoord = note.isHasCoordinates();
        content.put(KEY_NOTE_HAS_COORDINATES, String.format("%d", DBHelper.convertBooleanToInt(hasCoord)));
        content.put(KEY_NOTE_ADDRESS, note.getAddress());

        return content;
    }
}
