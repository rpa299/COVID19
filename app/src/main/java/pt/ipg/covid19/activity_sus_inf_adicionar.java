package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.database.Cursor;
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

public class activity_sus_inf_adicionar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_SUS_INF = 0;
    private Spinner nomePessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sus_inf_adicionar);

        nomePessoa = (Spinner)findViewById(R.id.spinnerPerfilSusInfAdd);
        getSpinnerPerfil(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SUS_INF, null, this);
    }

    public void escolherDataNascimento(View view){
        final TextView dataSintoma = findViewById(R.id.textViewDataNascimentoSusAdd);

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
        final TextView dataSintoma = findViewById(R.id.textViewDataInfSusAdd);

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

    private void getSpinnerPerfil(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTablePerfil.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );
        nomePessoa.setAdapter(adapter);
    }

    public void Guardar(View view){
        validarCampos();
    }

    public void validarCampos(){
        TextInputEditText mensagemNomeSusInfAdd = (TextInputEditText) findViewById(R.id.EditTextNomeSusAdd);
        TextView mensagemDataNascimentoSusInfAdd = (TextView) findViewById(R.id.textViewDataNascimentoSusAdd);
        TextView mensagemDataInfSusInfAdd = (TextView) findViewById(R.id.textViewDataInfSusAdd);

        //mete os valores em strings
        String nomeSus = mensagemNomeSusInfAdd.getText().toString();
        String dataNascimento = mensagemDataNascimentoSusInfAdd.getText().toString();
        String dataInf = mensagemDataInfSusInfAdd.getText().toString();

        long idPessoa = nomePessoa.getSelectedItemId();
        String pessoa = nomePessoa.getSelectedItem().toString();

        //validação
        if(nomeSus.trim().isEmpty()){
            mensagemNomeSusInfAdd.setError(getString(R.string.obrigatorio));
            mensagemNomeSusInfAdd.requestFocus();
            return;
        }
        if(dataNascimento.trim().isEmpty()){
            mensagemDataNascimentoSusInfAdd.setError(getString(R.string.obrigatorio));
            mensagemDataNascimentoSusInfAdd.requestFocus();
            return;
        }
        if(dataInf.trim().isEmpty()){
            mensagemDataInfSusInfAdd.setError(getString(R.string.obrigatorio));
            mensagemDataInfSusInfAdd.requestFocus();
            return;
        }

        //guardar dados
        SusInf susInf = new SusInf();
        susInf.setNomeSusInf(nomeSus);
        susInf.setDataNascimento(dataNascimento);
        susInf.setDataInfecao(dataInf);
        susInf.setIdPerfil(idPessoa);

        try {
            this.getContentResolver().insert(CovidContentProvider.ENDERECO_SUSINF, Converte.SusInfToContentValues(susInf));
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, R.string.Erro, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        getSpinnerPerfil(null);
    }
}
