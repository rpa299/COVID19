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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class activity_sintomas_adicionar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_SINTOMA = 0;
    private Spinner nomePessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_adicionar);

        Spinner spinnerDoresCabeca = findViewById(R.id.spinnerDoresCabecaAdd);
        ArrayAdapter<CharSequence> adapterDoresCabeca = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresCabeca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresCabeca.setAdapter(adapterDoresCabeca);
        spinnerDoresCabeca.setOnItemSelectedListener(this);

        Spinner spinnerDoresMusculares = findViewById(R.id.spinnerDoresMuscularesAdd);
        ArrayAdapter<CharSequence> adapterDoresMusculares = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresMusculares.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresMusculares.setAdapter(adapterDoresMusculares);
        spinnerDoresMusculares.setOnItemSelectedListener(this);

        Spinner spinnerCansaco = findViewById(R.id.spinnerCansacoAdd);
        ArrayAdapter<CharSequence> adapterCansaco = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterCansaco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCansaco.setAdapter(adapterCansaco);
        spinnerCansaco.setOnItemSelectedListener(this);

        Spinner spinnerDoresGarganta = findViewById(R.id.spinnerDoresGargantaAdd);
        ArrayAdapter<CharSequence> adapterDoresGarganta = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresGarganta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresGarganta.setAdapter(adapterDoresGarganta);
        spinnerDoresGarganta.setOnItemSelectedListener(this);

        Spinner spinnerTosse = findViewById(R.id.spinnerTosseAdd);
        ArrayAdapter<CharSequence> adapterTosse = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterTosse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTosse.setAdapter(adapterTosse);
        spinnerTosse.setOnItemSelectedListener(this);

        Spinner spinnerRespiracao = findViewById(R.id.spinnerRespiracaoAdd);
        ArrayAdapter<CharSequence> adapterRespiracao = ArrayAdapter.createFromResource(this, R.array.sintomas2, android.R.layout.simple_spinner_item);
        adapterRespiracao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRespiracao.setAdapter(adapterRespiracao);
        spinnerRespiracao.setOnItemSelectedListener(this);

        Spinner spinnerCorrimentoNasal = findViewById(R.id.spinnerCorrimentoNasalAdd);
        ArrayAdapter<CharSequence> adapterCorrimentoNasal = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterCorrimentoNasal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrimentoNasal.setAdapter(adapterCorrimentoNasal);
        spinnerCorrimentoNasal.setOnItemSelectedListener(this);

        nomePessoa = (Spinner)findViewById(R.id.spinnerPessoaAddSintoma);
        getSpinnerPerfil(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SINTOMA, null, this);
    }

    public void escolherData(View view){
        final TextView dataSintoma = findViewById(R.id.textViewSintomaDataAdd);

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
        ValidarCampos();
    }

    public void ValidarCampos(){
        TextView mensagemDataAdd = (TextView) findViewById(R.id.textViewSintomaDataAdd);
        Spinner spinnerDoresCabecaAdd = findViewById(R.id.spinnerDoresCabecaAdd);
        Spinner spinnerDoresMuscularesAdd = findViewById(R.id.spinnerDoresMuscularesAdd);
        Spinner spinnerCansacoAdd = findViewById(R.id.spinnerCansacoAdd);
        Spinner spinnerDoresGargantaAdd = findViewById(R.id.spinnerDoresGargantaAdd);
        Spinner spinnerTosseAdd = findViewById(R.id.spinnerTosseAdd);
        TextInputEditText mensagemTemperaturaAdd = (TextInputEditText) findViewById(R.id.editTextTemperaturaAdd);
        Spinner spinnerRespiracaoAdd = findViewById(R.id.spinnerRespiracaoAdd);
        Spinner spinnerCorrimentoNasalAdd = findViewById(R.id.spinnerCorrimentoNasalAdd);

        //mete os valores em strings
        String data = mensagemDataAdd.getText().toString();
        String doresCabeca = spinnerDoresCabecaAdd.getSelectedItem().toString();
        String doresMusculares = spinnerDoresMuscularesAdd.getSelectedItem().toString();
        String cansaco = spinnerCansacoAdd.getSelectedItem().toString();
        String doresGarganta = spinnerDoresGargantaAdd.getSelectedItem().toString();
        String tosse = spinnerTosseAdd.getSelectedItem().toString();
        String temperatura = mensagemTemperaturaAdd.getText().toString();
        String respiracao = spinnerRespiracaoAdd.getSelectedItem().toString();
        String corrimentoNasal = spinnerCorrimentoNasalAdd.getSelectedItem().toString();
        long idPessoa = nomePessoa.getSelectedItemId();
        String pessoa = nomePessoa.getSelectedItem().toString();

        //validação
        if(temperatura.trim().isEmpty()){
            mensagemTemperaturaAdd.setError(getString(R.string.obrigatorio));
            mensagemTemperaturaAdd.requestFocus();
            return;
        }

        Float temperaturaFloat = Float.parseFloat(temperatura);
        //guardar dados
        Sintoma sintoma = new Sintoma();
        sintoma.setData(data);
        sintoma.setDoresCabeca(doresCabeca);
        sintoma.setDoresMusculares(doresMusculares);
        sintoma.setCansaco(cansaco);
        sintoma.setDoresGarganta(doresGarganta);
        sintoma.setTosse(tosse);
        sintoma.setTemperatura(temperaturaFloat);
        sintoma.setRespiracao(respiracao);
        sintoma.setCorrimentoNasal(corrimentoNasal);
        sintoma.setIdPerfil(idPessoa);

        try {
            this.getContentResolver().insert(CovidContentProvider.ENDERECO_SINTOMAS, Converte.SintomaToContentValues(sintoma));
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
