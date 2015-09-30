package arf.com.everpobre.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notebook {


    private long id;
    private String name;
    private Date creationgDate;
    private Date modificationDate;


    private List<Note> notes;


    public Notebook(String name){
        this.name = name;
    }


    //Lazy getter
    public List<Note> allNotes(){

        if (this.notes == null){
            this.notes = new ArrayList<>();
        }
        return this.notes;
    }

    /**
     * This method adds a nonnull note to notes
     * @param note
     */
    public void addNote(@NonNull final Note note){
        if (note != null){
            this.allNotes().add(note);
            note.setNotebook(this);
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationgDate() {
        return creationgDate;
    }

    public void setCreationgDate(Date creationgDate) {
        this.creationgDate = creationgDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void addNote(String noteText) {
        Note note = new Note(noteText, this);
        allNotes().add(note);
    }
}
