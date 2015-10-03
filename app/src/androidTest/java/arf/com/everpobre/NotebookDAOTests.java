package arf.com.everpobre;

import android.database.Cursor;
import android.test.AndroidTestCase;

import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;
import arf.com.everpobre.model.db.DBHelper;

/**
 * Created by arodriguez on 9/30/15.
 */
public class NotebookDAOTests extends AndroidTestCase {

    public void testInsertNullNotebook() {

        Notebook notebook = null;

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertNotebookNotebookWithNullName() {

        Notebook notebook = new Notebook("");
        notebook.setName(null);

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertValidNotebook() {

        Notebook notebook = new Notebook("notebook");


        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertTrue(id > 0);

    }

    public void testNotebookCount() {

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        Notebook notebook = new Notebook("notebook");
        notebookDAO.insert(notebook);


        Cursor cursor = notebookDAO.queryCursor();

        //Con esto me aseguro que hay por lo menos un notebook en la tabla
        assertTrue(cursor.moveToFirst());

    }


}
