package arf.com.everpobre.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import arf.com.everpobre.R;
import arf.com.everpobre.fragment.DataGridFragment;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;
import arf.com.everpobre.providers.EverpobreProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

   DataGridFragment notebookFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notebookFragment = (DataGridFragment) getFragmentManager().findFragmentById(R.id.grid_fragment);

        LoaderManager loaderManager = getLoaderManager();

        //El 0 es un identificador único para este loader.
        loaderManager.initLoader(0,
                null,//Bundle parámetro
                this); //Delegado


        //Cursor cursor = new NotebookDAO(this).queryCursor();
        //notebookFragment.setCursor(cursor);

//        insertNotebookStubs(10);

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Cursor cursor = new NotebookDAO(this).queryCursor();
        notebookFragment.setCursor(cursor);
        notebookFragment.refreshData();*/
    }

    private void insertNotebookStubs(final int notebooksToInsert) {
        NotebookDAO notebookDAO = new NotebookDAO(this);
        for (int i = 0; i < notebooksToInsert; i++) {
            final String testTitle = String.format("%s %d", "Notebook title", i);
            final Notebook notebook = new Notebook(testTitle);
            long id = notebookDAO.insert(notebook);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_new_notebook) {

            Intent editNotebook = new Intent(this, EditNotebookActivity.class);
            startActivity(editNotebook);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        //Esto lo que hace es crear un hilo en 2do plano y pedirle al contentResolver q haga la consulta que le estoy pasando
        CursorLoader loader = new CursorLoader(this, EverpobreProvider.NOTEBOOKS_URI, NotebookDAO.allColumns,null,null,null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        notebookFragment.setCursor(cursor);

        notebookFragment.setIdLayout(R.layout.fragment_data_grid);
        notebookFragment.setIdGridView(R.id.grid_view);

        notebookFragment.refreshData();

        notebookFragment.setListener(new DataGridFragment.OnDataGridFragmentClickListener() {
            @Override
            public void dataGridElementClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, ShowNotebookActivity.class);
                startActivity(intent);
            }

            @Override
            public void dataGridElementLongClick(AdapterView<?> adapterView, View view, int position, long id) {


                //MainActivity.this es la forma de obtener el this de afuera del listener.
                NotebookDAO notebookDAO = new NotebookDAO(MainActivity.this);
                Notebook notebook = notebookDAO.query(id);

                Intent intent = new Intent(MainActivity.this, EditNotebookActivity.class);
                intent.putExtra(EditNotebookActivity.NOTEBOOK_EXTRA, notebook);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
