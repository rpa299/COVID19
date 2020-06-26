package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_perfil_delete extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private Perfil perfil;
    private Uri enderecoEliminarPerfil;
    private Button buttonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_delete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNomePerfilDelete);
        TextView textViewData = (TextView) findViewById(R.id.textViewDataNascimentoPerfilDelete);
        TextView textViewSexo = (TextView) findViewById(R.id.textViewSexoPerfilDelete);
        TextView textViewAltura = (TextView) findViewById(R.id.textViewAlturaPerfilDelete);
        TextView textViewPeso = (TextView) findViewById(R.id.textViewPesoPerfilDelete);
        TextView textViewTipoSangue = (TextView) findViewById(R.id.textViewTipoSanguePerfilDelete);

        buttonEliminar = (Button) findViewById(R.id.buttonConfirmarDeletePerfil);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        Intent intent = getIntent();

        long idPerfil = intent.getLongExtra(activity_perfil.ID_PERFIL,-1);

        if(idPerfil == -1){
            Toast.makeText(this, R.string.Erro, Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        enderecoEliminarPerfil = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PERFIL, String.valueOf(idPerfil));

        Cursor cursor = getContentResolver().query(enderecoEliminarPerfil, BdTablePerfil.TODOS_CAMPOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,R.string.Erro, Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        perfil = Perfil.fromCursor(cursor);

        textViewNome.setText(perfil.getNome());
        textViewData.setText(perfil.getDataNascimento());
        textViewSexo.setText(perfil.getSexo());
        textViewAltura.setText(""+perfil.getAltura());
        textViewPeso.setText(""+perfil.getPeso());
        textViewTipoSangue.setText(perfil.getTipoSangue());
    }

    public void eliminarPessoa() {
        int PessoasApagadas = getContentResolver().delete(enderecoEliminarPerfil, null, null);

        if (PessoasApagadas == 1) {
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.Erro, Toast.LENGTH_LONG).show();
        }
    }

    public void eliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.ApagarPessoa);
        builder.setMessage("" + R.string.Certeza + perfil.getNome());
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPessoa();
            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    public void Cancelar(View view){
        finish();
        Toast.makeText(this, R.string.cancelar, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}