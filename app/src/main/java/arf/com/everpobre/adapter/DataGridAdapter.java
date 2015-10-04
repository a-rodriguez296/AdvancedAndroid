package arf.com.everpobre.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import arf.com.everpobre.R;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arodriguez on 10/1/15.
 */
public class DataGridAdapter extends CursorAdapter {


    @Bind(R.id.text_notebook_name)  TextView txtTitle;
    @Bind(R.id.icon_notebook) ImageView itemImage;

    private LayoutInflater layoutInflater;
    private Cursor dataCursor;


    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);
        this.dataCursor = c;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        //Acá se crea la vista
        View view = layoutInflater.inflate(R.layout.view_notebook, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //Acá se pinta la vista
        Notebook notebook = NotebookDAO.notebookFromCursor(cursor);
        txtTitle.setText(notebook.getName());
    }

}
