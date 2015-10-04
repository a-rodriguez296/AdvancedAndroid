package arf.com.everpobre.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import arf.com.everpobre.R;
import arf.com.everpobre.fragment.DataGridFragment;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;

public class MainActivity extends AppCompatActivity {

   DataGridFragment notebookFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notebookFragment = (DataGridFragment) getFragmentManager().findFragmentById(R.id.grid_fragment);


//        insertNotebookStubs(10);

    }

    @Override
    protected void onResume() {
        super.onResume();
        notebookFragment.refreshData();
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


}
