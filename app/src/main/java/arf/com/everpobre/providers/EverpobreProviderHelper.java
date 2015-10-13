package arf.com.everpobre.providers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import arf.com.everpobre.EverpobreApp;
import arf.com.everpobre.model.Note;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NoteDAO;
import arf.com.everpobre.model.dao.NotebookDAO;
import arf.com.everpobre.model.db.DBConstants;
import arf.com.everpobre.model.db.DBHelper;

/**
 * Created by arodriguez on 10/7/15.
 */
public class EverpobreProviderHelper {

    // convenience methods: easy access to this content provider from within this project

    public static String getIdFromUri(Uri uri) {
        String rowID = uri.getPathSegments().get(1);
        return rowID;
    }

    public static Cursor getAllNotebooks() {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();

        Cursor cursor = cr.query(EverpobreProvider.NOTEBOOKS_URI, NotebookDAO.allColumns, null, null, null);

        return cursor;
    }

    public static Uri insertNotebook(Notebook notebook) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();
        if (notebook == null) {
            return null;
        }


        Uri uri = cr.insert(EverpobreProvider.NOTEBOOKS_URI, NotebookDAO.getContentValues(notebook));
        notebook.setId(Long.parseLong(getIdFromUri(uri)));
        return uri;
    }

    public static Uri insertNote(Notebook notebook, String text) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();

        Note n = new Note(text, notebook);
        Uri insertedUri = cr.insert(EverpobreProvider.NOTES_URI, NoteDAO.getContentValues(n));

        return insertedUri;
    }

    public static Uri insertNote(Notebook notebook, Note note) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();

        note.setNotebook(notebook);
        Uri insertedUri = cr.insert(EverpobreProvider.NOTES_URI, NoteDAO.getContentValues(note));

        return insertedUri;
    }

    public static void deleteNotebook(Notebook notebook) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();
        String sUri = EverpobreProvider.NOTEBOOKS_URI.toString() + "/" + notebook.getId();
        Uri uri = Uri.parse(sUri);
        cr.delete(uri, null, null);
    }

    /**
     * Deletes a notebook using its id
     * @param id
     */
    public static void deleteNotebook(long id) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();
        String sUri = EverpobreProvider.NOTEBOOKS_URI.toString() + "/" + id;
        Uri uri = Uri.parse(sUri);
        cr.delete(uri, null, null);
    }

    /**
     *
     * @param notebook
     * @return all notes from a Notebook object
     */
    public static Cursor getAllNotes(Notebook notebook) {
        return getAllNotes(notebook.getId());
    }

    /**
     *
     * @param notebookId id of the notebook whose notes we want
     * @return
     */
    public static Cursor getAllNotes(long notebookId) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();

        Cursor c = cr.query(EverpobreProvider.NOTES_URI, null, DBConstants.KEY_NOTE_NOTEBOOK + "=" + notebookId, null, null);

        return c;
    }

    public static void deleteNote(long id) {
        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();
        String sUri = EverpobreProvider.NOTES_URI.toString() + "/" + id;
        Uri uri = Uri.parse(sUri);
        cr.delete(uri, null, null);
    }


    public static int updateNotebook(Notebook notebook) {
        if (notebook == null) {
            return (int)DBHelper.INVALID_ID;
        }

        ContentResolver cr = EverpobreApp.getAppContext().getContentResolver();
        String sUri = EverpobreProvider.NOTEBOOKS_URI.toString() + "/" + notebook.getId();
        Uri uri = Uri.parse(sUri);


        int updatedNotebooks = cr.update(uri,NotebookDAO.getContentValues(notebook), null,null);
        return updatedNotebooks;
    }
}
