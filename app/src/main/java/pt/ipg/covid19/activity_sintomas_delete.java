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

public class activity_sintomas_delete extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private Sintoma sintoma;
    private Uri enderecoEliminarSintoma;
    private Button buttonEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView nome = findViewById(R.id.textViewNomeDeleteSintoma2);
        TextView data = findViewById(R.id.textViewDataDeleteSintoma2);
        TextView doresCabeca = findViewById(R.id.textViewDoresCabecaDeleteSintoma2);
        TextView doresMusculares = findViewById(R.id.textViewDoresMuscularesDeleteSintoma2);
        TextView cansaco = findViewById(R.id.textViewCansacoDeleteSintoma2);
        TextView doresGarganta = findViewById(R.id.textViewDoresGargantaDeleteSintoma2);
        TextView tosse = findViewById(R.id.textViewTosseDeleteSintoma2);
        TextView Temperatura = findViewById(R.id.textViewTemperaturaDeleteSintoma2);
        TextView respiracao = findViewById(R.id.textViewRespiracaoDeleteSintoma2);
        TextView corrimentoNasal = findViewById(R.id.textViewCorrimentoNasalDeleteSintoma2);

        Intent intent = getIntent();
        sintoma = (Sintoma) intent.getSerializableExtra("SintomaDelete");
        enderecoEliminarSintoma = Uri.withAppendedPath(CovidContentProvider.ENDERECO_SINTOMAS, String.valueOf(sintoma.getId()));

        buttonEliminar = (Button) findViewById(R.id.buttonConfirmarDeleteSintoma);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });

        nome.setText(sintoma.getNomePerfil());
        data.setText(sintoma.getData());
        doresCabeca.setText(sintoma.getDoresCabeca());
        doresMusculares.setText(sintoma.getDoresMusculares());
        cansaco.setText(sintoma.getCansaco());
        doresGarganta.setText(sintoma.getDoresGarganta());
        tosse.setText(sintoma.getTosse());
        Temperatura.setText(""+sintoma.getTemperatura());
        respiracao.setText(sintoma.getRespiracao());
        corrimentoNasal.setText(sintoma.getCorrimentoNasal());
    }

    public void eliminarSintoma() {
        int SintomaApagado = getContentResolver().delete(enderecoEliminarSintoma, null, null);

        if (SintomaApagado == 1) {
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, R.string.Erro, Toast.LENGTH_LONG).show();
        }
    }

    public void eliminar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.ApagarSintoma);
        builder.setMessage(R.string.Certeza);
        builder.setIcon(R.drawable.ic_delete);
        builder.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarSintoma();
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