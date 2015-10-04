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
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by arodriguez on 10/1/15.
 */
public class DataGridAdapter extends CursorAdapter {




    private LayoutInflater layoutInflater;
    private Cursor cursor;


    public DataGridAdapter(Context context, Cursor c) {
        super(context, c);

        this.layoutInflater = LayoutInflater.from(context);
        this.cursor = c;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;


        //Verificar si ya existe una celda
        if (view != null){

            //Si ya existe la vista, le pido el holder.
            holder = (ViewHolder) view.getTag();


        }
        else{
            view = layoutInflater.inflate(R.layout.view_notebook, parent, false);
            holder = new ViewHolder(view);

            //Asociarle a view el holder para luego utilizarlo en el view != null
            view.setTag(holder);
        }

        Notebook notebook = NotebookDAO.notebookFromCursor(cursor);
        holder.txtTitle.setText(notebook.getName());

        return view;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }

    //Esto es el patrón viewHolder
    static class ViewHolder{

        @Bind(R.id.text_notebook_name)  TextView txtTitle;
        @Bind(R.id.icon_notebook) ImageView itemImage;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
