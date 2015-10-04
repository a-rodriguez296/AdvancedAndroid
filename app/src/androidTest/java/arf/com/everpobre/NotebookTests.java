package arf.com.everpobre;

import android.database.Cursor;
import android.test.AndroidTestCase;

import arf.com.everpobre.model.Note;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;
import arf.com.everpobre.model.db.DBHelper;

public class NotebookTests extends AndroidTestCase{


    public void testInsertNullNotebookReturnsInvalidId() {
        Notebook notebook = null;

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertNotebookWithNullNameReturnsInvalidId() {
        Notebook notebook = new Notebook("");
        notebook.setName(null);

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertEquals(id, DBHelper.INVALID_ID);
    }

    public void testInsertNotebookReturnsValidId() {
        Notebook notebook = new Notebook("Test title");

        NotebookDAO notebookDAO = new NotebookDAO(getContext());

        long id = notebookDAO.insert(notebook);

        assertTrue(id > 0);
    }

    public void testQueryAllNotebooks() {
        insertNotebookStubs(10);

        final NotebookDAO notebookDAO = new NotebookDAO(getContext());
        final Cursor cursor = notebookDAO.queryCursor();
        final int notebookCount = cursor.getCount();

        assertTrue(notebookCount > 9);
    }

    private void insertNotebookStubs(final int notebooksToInsert) {

        NotebookDAO notebookDAO = new NotebookDAO(getContext());
        for (int i = 0; i < notebooksToInsert; i++) {
            final String testTitle = String.format("%s %d", "Test title", i);
            final Notebook notebook = new Notebook(testTitle);
            long id = notebookDAO.insert(notebook);
        }

    }

    public void testDeleteAllNotebooks() {
        insertNotebookStubs(10);

        final NotebookDAO notebookDAO = new NotebookDAO(getContext());
        notebookDAO.deleteAll();

        final Cursor cursor = notebookDAO.queryCursor();
        final int notebookCount = cursor.getCount();

        assertEquals(0, notebookCount);
    }

    public void testUpdateOneNotebook() {
        final NotebookDAO notebookDAO = new NotebookDAO(getContext());
        final Notebook notebook = new Notebook("Change me if you dare");

        final long id = notebookDAO.insert(notebook);

        final Notebook originalCopyNotebook = notebookDAO.query(id);
        assertEquals("Change me if you dare", originalCopyNotebook.getName());

        originalCopyNotebook.setName("Challenge accepted");
        notebookDAO.update(id, originalCopyNotebook);

        final Notebook afterChangeNotebook = notebookDAO.query(id);
        assertEquals("Challenge accepted", afterChangeNotebook.getName());
    }


}
