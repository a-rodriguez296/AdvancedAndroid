package arf.com.everpobre;

import android.test.AndroidTestCase;

import arf.com.everpobre.model.Note;
import arf.com.everpobre.model.Notebook;

public class NoteTests extends AndroidTestCase{

    public void testCanCreateANote(){

        Note note = new Note("Note name", new Notebook("Note text"));

        assertNotNull(note);

    }

}
