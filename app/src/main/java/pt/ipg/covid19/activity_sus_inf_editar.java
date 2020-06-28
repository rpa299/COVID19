package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class activity_sus_inf_editar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSOR_LOADER_SUS_INF = 0;
    private SusInf susInf;
    private Spinner Nomespinner;
    private TextView dataNascimento, dataInf;
    private TextInputEditText NomeSus;
    private boolean PerfilCarregado = false;
    private boolean PerfilAtualizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sus_inf_editar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Nomespinner = findViewById(R.id.spinnerPerfilSusInfEdit);
        NomeSus = findViewById(R.id.editTextNomeSusEdit);
        dataNascimento = findViewById(R.id.textViewDataNascimentoSusEdit);
        dataInf = findViewById(R.id.textViewSusInfDateEdit);

        getSpinnerPerfil(null);
        Intent intent = getIntent();
        susInf = (SusInf) intent.getSerializableExtra("SusInfEdit");
        NomeSus.setText(susInf.getNomeSusInf());
        dataNascimento.setText(susInf.getDataNascimento());
        dataInf.setText(susInf.getDataInfecao());

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SUS_INF, null, this);

        actualizaPerfilSelecionado();
    }

    public void escolherDataNascimento(View view){
        final TextView dataSintoma = findViewById(R.id.textViewDataNascimentoSusEdit);

        final Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);

                CharSequence dateCharSequence = DateFormat.format("dd/MM/yyyy",calendar1);

                dataSintoma.setText(dateCharSequence);

            }
        }, YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    public void escolherDataInf(View view){
        final TextView dataSintoma = findViewById(R.id.textViewSusInfDateEdit);

        final Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);

                CharSequence dateCharSequence = DateFormat.format("dd/MM/yyyy",calendar1);

                dataSintoma.setText(dateCharSequence);

            }
        }, YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    private void actualizaPerfilSelecionado() {
        if (!PerfilCarregado) return;
        if (PerfilAtualizado) return;
        long idPerfil = susInf.getIdPerfil();
        for (int i= 0; i < Nomespinner.getCount(); i++) {
            if (Nomespinner.getItemIdAtPosition(i) == idPerfil) {
                Nomespinner.setSelection(i, true);
                break;
            }
        }
        PerfilAtualizado = true;
    }

    private void getSpinnerPerfil(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTablePerfil.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        Nomespinner.setAdapter(adapter);
    }

    public void Guardar(View view){
        validarCampo();
    }

    public void validarCampo(){
        TextInputEditText mensagemNomeSusEdit = (TextInputEditText) findViewById(R.id.editTextNomeSusEdit);
        TextView mensagemDataNascimentoEdit = (TextView) findViewById(R.id.textViewDataNascimentoSusEdit);
        TextView mensagemDataInfEdit = (TextView) findViewById(R.id.textViewSusInfDateEdit);

        //mete os valores em strings
        String Nome = mensagemNomeSusEdit.getText().toString();
        String dataN = mensagemDataNascimentoEdit.getText().toString();
        String dataI = mensagemDataInfEdit.getText().toString();
        long idPessoa = Nomespinner.getSelectedItemId();
        String pessoa = Nomespinner.getSelectedItem().toString();

        //validação
        if(Nome.trim().isEmpty()){
            mensagemNomeSusEdit.setError(getString(R.string.obrigatorio));
            mensagemNomeSusEdit.requestFocus();
            return;
        }

        //guardar dados
        susInf.setNomeSusInf(Nome);
        susInf.setDataNascimento(dataN);
        susInf.setDataInfecao(dataI);
        susInf.setIdPerfil(idPessoa);

        try {
            Uri enderecoSusInf = Uri.withAppendedPath(CovidContentProvider.ENDERECO_SUSINF, String.valueOf(susInf.getId()));

            int registos = this.getContentResolver().update(enderecoSusInf, Converte.SusInfToContentValues(susInf), null, null);

            if (registos == 1) {
                Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
        }
    }

    public void cancelar(View view){
        finish();
        Toast.makeText(this, ("Cancelado"),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PERFIL, BdTablePerfil.TODOS_CAMPOS, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        getSpinnerPerfil(data);
        PerfilCarregado = true;
        actualizaPerfilSelecionado();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        getSpinnerPerfil(null);
    }
}