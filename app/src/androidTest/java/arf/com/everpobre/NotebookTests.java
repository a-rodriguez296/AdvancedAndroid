package arf.com.everpobre;

import android.test.AndroidTestCase;

import arf.com.everpobre.model.Note;
import arf.com.everpobre.model.Notebook;

public class NotebookTests extends AndroidTestCase{


    private final String mNotebook = "notebook";
    private final String mName = "Note name";

    public void testCanCreateNotebook(){

        Notebook notebook = new Notebook(mNotebook);
        assertNotNull(notebook);
        assertEquals(mNotebook,notebook.getName());
    }

    public void testCanAddNotesToNotebook(){

        Notebook notebook = new Notebook(mNotebook);
        assertEquals(0, notebook.allNotes().size());

        Note note = new Note(mName, notebook);
        notebook.addNote(note);


        assertEquals(1, notebook.allNotes().size());
    }

    public void testCanAddNotesToANoteBookWithNoteText(){
        Notebook notebook = new Notebook(mNotebook);

        assertEquals(0, notebook.allNotes().size());

        notebook.addNote("my note text");

        assertEquals(1, notebook.allNotes().size());


    }


}
