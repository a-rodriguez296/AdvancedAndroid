package arf.com.everpobre.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import arf.com.everpobre.R;
import arf.com.everpobre.activities.EditNotebookActivity;
import arf.com.everpobre.activities.ShowNotebookActivity;
import arf.com.everpobre.adapter.DataGridAdapter;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;


public class DataGridFragment extends Fragment {

    private GridView gridView;
    private DataGridAdapter adapter;
    private Cursor cursor;
    private int idLayout = R.layout.fragment_data_grid;
    private int idGridView;


    private OnDataGridFragmentClickListener mListener;

    public static DataGridFragment getInstance(Cursor cursor, int idLayout, int idGridView){
        DataGridFragment fragment = new DataGridFragment();

        fragment.cursor = cursor;
        fragment.idLayout = idLayout;
        fragment.idGridView = idGridView;

        return fragment;
    }


    public DataGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(idLayout, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = (GridView) getActivity().findViewById(idGridView);

        if (gridView!=null){
            refreshData();
        }


    }

    public void refreshData() {

        if (cursor == null){
            return;
        }

        gridView = (GridView) getActivity().findViewById(idGridView);

        adapter = new DataGridAdapter(getActivity(),cursor);
        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (mListener != null) {
                    mListener.dataGridElementClick(adapterView, view, i, l);
                }

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (mListener != null) {
                    mListener.dataGridElementLongClick(adapterView, view, position, id);
                }
                return true;
            }
        });

    }


    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public int getIdLayout() {
        return idLayout;
    }

    public void setIdLayout(int idLayout) {
        this.idLayout = idLayout;
    }

    public int getIdGridView() {
        return idGridView;
    }

    public void setIdGridView(int idGridView) {
        this.idGridView = idGridView;
    }

    public OnDataGridFragmentClickListener getListener() {
        return mListener;
    }

    public void setListener(OnDataGridFragmentClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnDataGridFragmentClickListener{

        //Short click
        void dataGridElementClick(AdapterView<?> adapterView, View view, int i, long l);

        //Long click
        void dataGridElementLongClick(AdapterView<?> adapterView, View view, int position, long id);

    }
}