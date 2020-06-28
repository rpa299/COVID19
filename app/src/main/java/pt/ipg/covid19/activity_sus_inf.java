package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class activity_sus_inf extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String ID_SUS_INF = "ID_SUS_INF";
    public static final int ID_CURSOR_LOADER_SUS_INF = 0;
    private AdaptadorSusInf adaptadorSusInf;
    private RecyclerView recyclerViewSusInf;
    private Menu menu;
    private SusInf susInf = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sus_inf);

        recyclerViewSusInf = (RecyclerView) findViewById(R.id.recyclerViewSusInf);
        adaptadorSusInf = new AdaptadorSusInf(this);
        recyclerViewSusInf.setAdapter(adaptadorSusInf);
        recyclerViewSusInf.setLayoutManager(new LinearLayoutManager(this));

        adaptadorSusInf.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SUS_INF, null, this);
    }

    public void susInfAlterado(SusInf susInf) {
        this.susInf = susInf;

        boolean mostraEditarEliminar = (susInf != null);

        menu.findItem(R.id.action_moreEdit).setVisible(mostraEditarEliminar);
        menu.findItem(R.id.action_moreDelete).setVisible(mostraEditarEliminar);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SUS_INF, null, this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_moreAdd) {
            Intent intent = new Intent(this, activity_sus_inf_adicionar.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_moreEdit) {
            Intent intent = new Intent(this, activity_sus_inf_editar.class);
            intent.putExtra("SusInfEdit", susInf);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_moreDelete){
            Intent intent = new Intent(this, activity_sus_inf_delete.class);
            intent.putExtra("SusInfDelete", susInf);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_SUSINF, BdTableSusInf.TODOS_CAMPOS, null, null, BdTableSusInf.CAMPO_NOME_SUS_INF);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorSusInf.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorSusInf.setCursor(null);
    }
}