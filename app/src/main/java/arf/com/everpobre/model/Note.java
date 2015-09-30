package arf.com.everpobre.model;

import java.lang.ref.WeakReference;
import java.util.Date;

public class Note {

    private long id;
    private String text;
    private Date creationgDate;
    private Date modificationDate;
    private String photoURL;
    private WeakReference<Notebook>  notebook;
    private double longitude;
    private double latitude;
    public boolean hasCoordinate;
    public String address;

    public Note(String name, Notebook notebook) {
        this.text = name;
        this.notebook = new WeakReference(notebook);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Notebook getNotebook() {
        return notebook.get();
    }

    public void setNotebook(Notebook notebook) {
        this.notebook = new WeakReference<Notebook>(notebook) ;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isHasCoordinate() {
        return hasCoordinate;
    }

    public void setHasCoordinate(boolean hasCoordinate) {
        this.hasCoordinate = hasCoordinate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
