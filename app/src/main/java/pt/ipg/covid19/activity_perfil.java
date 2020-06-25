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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class activity_perfil extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_PERFIL = "ID_PERFIL";

    private AdaptadorPerfil adaptadorPerfil;
    private RecyclerView recyclerViewPerfil;
    private Menu menu;

    public static final int ID_CURSOR_LOADER_PERFIL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PERFIL, null, this);
        recyclerViewPerfil = (RecyclerView) findViewById(R.id.recyclerViewPerfil);
        adaptadorPerfil = new AdaptadorPerfil(this);
        recyclerViewPerfil.setAdapter(adaptadorPerfil);
        recyclerViewPerfil.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adaptadorPerfil.setCursor(null);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_PERFIL, null, this);
        super.onResume();
    }

    public void atualizaOpcoesMenu() {
        Perfil perfilModel = adaptadorPerfil.getPerfilSelecionado();

        boolean mostraAlterarEliminar = (perfilModel != null);
        menu.findItem(R.id.action_moreEdit).setVisible(mostraAlterarEliminar);
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

        if(id == R.id.action_moreAdd){
            Intent intent = new Intent(this, activity_perfil_adicionar.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_moreEdit) {
            Intent intent = new Intent(this, activity_perfil_editar.class);
            intent.putExtra(ID_PERFIL, adaptadorPerfil.getPerfilSelecionado().getId());
            startActivity(intent);
        }
        else if(id == R.id.action_moreDelete){
            Intent intent = new Intent(this, activity_perfil_delete.class);
            intent.putExtra(ID_PERFIL, adaptadorPerfil.getPerfilSelecionado().getId());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PERFIL,
                BdTablePerfil.TODOS_CAMPOS, null, null, BdTablePerfil.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorPerfil.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
