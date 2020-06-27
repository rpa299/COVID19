package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class activity_sintomas extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    public static final String ID_SINTOMA = "ID_SINTOMA";
    public static final int ID_CURSOR_LOADER_SINTOMA = 0;
    private AdaptadorSintoma adaptadorSintoma;
    private RecyclerView recyclerViewSintoma;
    private Menu menu;
    private Sintoma sintoma = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas);

        recyclerViewSintoma = (RecyclerView) findViewById(R.id.recyclerViewSintoma);
        adaptadorSintoma = new AdaptadorSintoma(this);
        recyclerViewSintoma.setAdapter(adaptadorSintoma);
        recyclerViewSintoma.setLayoutManager(new LinearLayoutManager(this));

        adaptadorSintoma.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SINTOMA, null, this);
    }

    public void sintomaAlterado(Sintoma sintoma) {
        this.sintoma = sintoma;

        boolean mostraEditarEliminar = (sintoma != null);

        menu.findItem(R.id.action_moreEdit).setVisible(mostraEditarEliminar);
        menu.findItem(R.id.action_moreDelete).setVisible(mostraEditarEliminar);
    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SINTOMA, null, this);
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
            Intent intent = new Intent(this, activity_sintomas_adicionar.class);
            startActivity(intent);
            return true;
        }
        else if(id == R.id.action_moreEdit) {
                Intent intent = new Intent(this, activity_sintomas_editar.class);
                intent.putExtra("SintomaEdit", sintoma);
                startActivity(intent);
                return true;
        }
        else if(id == R.id.action_moreDelete){
                Intent intent = new Intent(this, activity_sintomas_delete.class);
                intent.putExtra("SintomaDelete",sintoma);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_SINTOMAS, BdTableSintoma.TODOS_CAMPOS, null, null, BdTableSintoma.CAMPO_DATA);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorSintoma.setCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorSintoma.setCursor(null);
    }
}