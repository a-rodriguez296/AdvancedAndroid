package arf.com.everpobre.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Notebook implements Parcelable {


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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeLong(creationgDate != null ? creationgDate.getTime() : -1);
        dest.writeLong(modificationDate != null ? modificationDate.getTime() : -1);
        dest.writeList(this.notes);
    }

    protected Notebook(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        long tmpCreationgDate = in.readLong();
        this.creationgDate = tmpCreationgDate == -1 ? null : new Date(tmpCreationgDate);
        long tmpModificationDate = in.readLong();
        this.modificationDate = tmpModificationDate == -1 ? null : new Date(tmpModificationDate);
        this.notes = new ArrayList<Note>();
        in.readList(this.notes, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<Notebook> CREATOR = new Parcelable.Creator<Notebook>() {
        public Notebook createFromParcel(Parcel source) {
            return new Notebook(source);
        }

        public Notebook[] newArray(int size) {
            return new Notebook[size];
        }
    };
}
