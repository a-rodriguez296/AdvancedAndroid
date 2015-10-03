package arf.com.everpobre.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import arf.com.everpobre.R;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;

/**
 * Created by arodriguez on 10/1/15.
 */
public class DataGridAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;


    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        //Cell for row at index path

        //view gruop representa el grid view
        View view = layoutInflater.inflate(R.layout.view_notebook,viewGroup,false);



        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ImageView itemImage = (ImageView) view.findViewById(R.id.icon_notebook);
        TextView txtItem = (TextView) view.findViewById(R.id.text_notebook_name);

        Notebook notebook = NotebookDAO.notebookFromCursor(cursor);

        txtItem.setText(notebook.getName());



    }
}
