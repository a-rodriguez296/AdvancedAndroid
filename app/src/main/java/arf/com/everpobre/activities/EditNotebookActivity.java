package arf.com.everpobre.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import arf.com.everpobre.R;
import arf.com.everpobre.model.Notebook;
import arf.com.everpobre.model.dao.NotebookDAO;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EditNotebookActivity extends AppCompatActivity {

    public static final String NOTEBOOK_EXTRA = "EditNotebookActivity.NOTEBOOK_EXTRA";

    enum EditMode{
        ADDING,
        EDITING,
        DELETING
    }


    private EditMode editMode;
    private Notebook notebook;
    @Bind(R.id.txt_notebook_name)
    EditText txtNotebookName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notebook);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        this.notebook = intent.getParcelableExtra(NOTEBOOK_EXTRA);
        if (notebook== null){
            editMode = EditMode.ADDING;
        }else{
            editMode = EditMode.EDITING;
            txtNotebookName.setText(notebook.getName());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_notebook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_save_notebook:
                saveNotebook();
                break;
            case R.id.action_delete_notebook:
                deleteNotebook();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNotebook() {

        if (notebook== null){
            return;
        }
        final NotebookDAO notebookDAO = new NotebookDAO(this);
        notebookDAO.delete(notebook.getId());

        finish();

    }

    private void saveNotebook() {

        final String notebookName = txtNotebookName.getText().toString();

        if (notebookName.isEmpty()){
            Toast.
                    makeText(this,"No puedes guardar un Notebook vacío", Toast.LENGTH_SHORT).
                    show();
            return;
        }

        final NotebookDAO notebookDAO = new NotebookDAO(this);

        if (editMode == EditMode.ADDING){
            final Notebook notebookToAdd = new Notebook(txtNotebookName.getText().toString());
            notebookDAO.insert(notebookToAdd);
        }
        else {
            this.notebook.setName(notebookName);
            notebookDAO.update(this.notebook.getId(), this.notebook);
        }
        finish();
    }
}
