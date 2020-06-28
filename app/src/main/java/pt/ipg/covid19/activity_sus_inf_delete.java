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

public class activity_sus_inf_delete extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private SusInf susInf;
    private Uri enderecoEliminarSusInf;
    private Button buttonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sus_inf_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView Utilizador = findViewById(R.id.textViewUtilizadorSusInfDelete2);
        TextView NomeSus = findViewById(R.id.textViewNomeSusInfDelete2);
        TextView dataNascimento = findViewById(R.id.textViewDataNascimentoSusInfDelete2);
        TextView dataInf = findViewById(R.id.textViewDataInfecaoSusInfDelete2);

        Intent intent = getIntent();
        susInf = (SusInf) intent.getSerializableExtra("SusInfDelete");
        enderecoEliminarSusInf = Uri.withAppendedPath(CovidContentProvider.ENDERECO_SUSINF, String.valueOf(susInf.getId()));

        buttonEliminar = (Button) findViewById(R.id.buttonConfirmarDeleteSusInf);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        Utilizador.setText(susInf.getNomePerfil());
        NomeSus.setText(susInf.getNomeSusInf());
        dataNascimento.setText(susInf.getDataNascimento());
        dataInf.setText(susInf.getDataInfecao());
    }

    public void eliminarSusInf() {
        int SusInfApagado = getContentResolver().delete(enderecoEliminarSusInf, null, null);

        if (SusInfApagado == 1) {
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.Erro, Toast.LENGTH_LONG).show();
        }
    }
    public void eliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.ApagarSusInf);
        builder.setMessage(R.string.Certeza);
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarSusInf();
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