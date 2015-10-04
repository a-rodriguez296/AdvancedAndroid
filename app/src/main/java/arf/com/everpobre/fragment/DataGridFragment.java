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

import arf.com.everpobre.R;
import arf.com.everpobre.activities.EditNotebookActivity;
import arf.com.everpobre.adapter.DataGridAdapter;
import arf.com.everpobre.model.Notebook;
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
        refreshData();

    }

    public void refreshData() {


        cursor = new NotebookDAO(getActivity()).queryCursor();
        adapter = new DataGridAdapter(getActivity(),cursor);
        gridView.setAdapter(adapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "se hizo click", Toast.LENGTH_SHORT).show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), "se hizo long click", Toast.LENGTH_SHORT).show();

                NotebookDAO notebookDAO = new NotebookDAO(getActivity());
                Notebook notebook = notebookDAO.query(id);


                Intent intent = new Intent(getActivity(), EditNotebookActivity.class);
                intent.putExtra(EditNotebookActivity.NOTEBOOK_EXTRA, notebook);
                startActivity(intent);


                /*Si se pone true quiere decir que yo ya hice el handle del onClick
                por tal razón no pasa al onItemClick*/
                return true;
            }
        });

    }
}