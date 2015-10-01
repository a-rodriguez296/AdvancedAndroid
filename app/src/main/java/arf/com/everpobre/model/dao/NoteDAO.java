package arf.com.everpobre.model.dao;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import arf.com.everpobre.model.Note;

/**
 * Created by arodriguez on 9/30/15.
 */
public class NoteDAO implements DaoPersistable<Note> {
    @Override
    public long insert(@NonNull Note data) {
        return 0;
    }

    @Override
    public void update(long id, @NonNull Note data) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void delete(@NonNull Note data) {

    }

    @Override
    public void deleteAll() {

    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        return null;
    }

    @Override
    public Note query(long id) {
        return null;
    }
}
