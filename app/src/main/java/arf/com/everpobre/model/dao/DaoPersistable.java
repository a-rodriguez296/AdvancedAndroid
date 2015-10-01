package arf.com.everpobre.model.dao;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import arf.com.everpobre.model.Notebook;

/**
 * Created by arodriguez on 9/30/15.
 */
public interface DaoPersistable<T> {

    long insert(@NonNull T data);


    void update(long id,@NonNull T data);

    void delete (long id);


    void delete (@NonNull T data);

    void deleteAll();

    @Nullable Cursor queryCursor();

    T query(long id);

}
