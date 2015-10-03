package arf.com.everpobre.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import arf.com.everpobre.R;
import arf.com.everpobre.adapter.DataGridAdapter;
import arf.com.everpobre.model.dao.NotebookDAO;


public class DataGridFragment extends Fragment {


    private GridView gridView;
    private DataGridAdapter adapter;
    private Cursor cursor;

    public DataGridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_grid, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        gridView = (GridView) getActivity().findViewById(R.id.grid_view);
        cursor = new NotebookDAO(getActivity()).queryCursor();
        adapter = new DataGridAdapter(getActivity(),cursor);
        gridView.setAdapter(adapter);
    }
}