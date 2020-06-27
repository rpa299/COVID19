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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class activity_sintomas_editar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSOR_LOADER_SINTOMAS = 0;
    private Sintoma sintoma;
    private Spinner Nomespinner;
    private TextView data;
    private TextInputEditText temperatura;
    private boolean PerfilCarregado = false;
    private boolean PerfilAtualizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sintomas_editar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Nomespinner = findViewById(R.id.spinnerPessoaSintomaEdit);
        data = findViewById(R.id.textViewSintomaDataEdit);
        temperatura = findViewById(R.id.editTextTemperatura);

        Spinner spinnerDoresCabeca = findViewById(R.id.spinnerDoresCabecaEdit);
        ArrayAdapter<CharSequence> adapterDoresCabeca = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresCabeca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresCabeca.setAdapter(adapterDoresCabeca);
        spinnerDoresCabeca.setOnItemSelectedListener(this);

        Spinner spinnerDoresMusculares = findViewById(R.id.spinnerDoresMuscularesEdit);
        ArrayAdapter<CharSequence> adapterDoresMusculares = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresMusculares.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresMusculares.setAdapter(adapterDoresMusculares);
        spinnerDoresMusculares.setOnItemSelectedListener(this);

        Spinner spinnerCansaco = findViewById(R.id.spinnerCansacoEdit);
        ArrayAdapter<CharSequence> adapterCansaco = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterCansaco.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCansaco.setAdapter(adapterCansaco);
        spinnerCansaco.setOnItemSelectedListener(this);

        Spinner spinnerDoresGarganta = findViewById(R.id.spinnerDoresGargantaEdit);
        ArrayAdapter<CharSequence> adapterDoresGarganta = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterDoresGarganta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoresGarganta.setAdapter(adapterDoresGarganta);
        spinnerDoresGarganta.setOnItemSelectedListener(this);

        Spinner spinnerTosse = findViewById(R.id.spinnerTosseEdit);
        ArrayAdapter<CharSequence> adapterTosse = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterTosse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTosse.setAdapter(adapterTosse);
        spinnerTosse.setOnItemSelectedListener(this);

        Spinner spinnerRespiracao = findViewById(R.id.spinnerRespiracaoEdit);
        ArrayAdapter<CharSequence> adapterRespiracao = ArrayAdapter.createFromResource(this, R.array.sintomas2, android.R.layout.simple_spinner_item);
        adapterRespiracao.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRespiracao.setAdapter(adapterRespiracao);
        spinnerRespiracao.setOnItemSelectedListener(this);

        Spinner spinnerCorrimentoNasal = findViewById(R.id.spinnerCorrimentoNasalEdit);
        ArrayAdapter<CharSequence> adapterCorrimentoNasal = ArrayAdapter.createFromResource(this, R.array.sintomas1, android.R.layout.simple_spinner_item);
        adapterCorrimentoNasal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCorrimentoNasal.setAdapter(adapterCorrimentoNasal);
        spinnerCorrimentoNasal.setOnItemSelectedListener(this);

        getSpinnerPerfil(null);
        Intent intent = getIntent();
        sintoma = (Sintoma) intent.getSerializableExtra("SintomaEdit");
        data.setText(sintoma.getData());
        spinnerDoresCabeca.setSelection(getSpinnerDoresCabeca(spinnerDoresCabeca,sintoma.getDoresCabeca()));
        spinnerDoresMusculares.setSelection(getSpinnerDoresMusculares(spinnerDoresMusculares,sintoma.getDoresMusculares()));
        spinnerCansaco.setSelection(getSpinnerCansaco(spinnerCansaco,sintoma.getCansaco()));
        spinnerDoresGarganta.setSelection(getSpinnerDoresGarganta(spinnerDoresGarganta,sintoma.getDoresGarganta()));
        spinnerTosse.setSelection(getSpinnerTosse(spinnerTosse,sintoma.getTosse()));
        temperatura.setText(""+sintoma.getTemperatura());
        spinnerRespiracao.setSelection(getSpinnerRespiracao(spinnerRespiracao,sintoma.getRespiracao()));
        spinnerCorrimentoNasal.setSelection(getSpinnerCorrimentoNasal(spinnerCorrimentoNasal,sintoma.getCorrimentoNasal()));

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_SINTOMAS, null, this);

        actualizaPerfilSelecionado();
    }

    public void escolherData(View view){
        final TextView dataSintoma = findViewById(R.id.textViewSintomaDataEdit);

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
        long idPerfil = sintoma.getIdPerfil();
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
    private int getSpinnerDoresCabeca(Spinner spinner, String doresCabeca){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(doresCabeca)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerDoresMusculares(Spinner spinner, String doresMusculares){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(doresMusculares)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerCansaco(Spinner spinner, String cansaco){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(cansaco)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerDoresGarganta(Spinner spinner, String doresGarganta){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(doresGarganta)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerTosse(Spinner spinner, String tosse){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(tosse)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerRespiracao(Spinner spinner, String respiracao){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(respiracao)){
                index = i;
            }
        }
        return index;
    }
    private int getSpinnerCorrimentoNasal(Spinner spinner, String CorrimentoNasal){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(CorrimentoNasal)){
                index = i;
            }
        }
        return index;
    }

    public void Guardar(View view){
        validarCampos();
    }

    public void validarCampos(){
        TextView mensagemDataEdit = (TextView) findViewById(R.id.textViewSintomaDataEdit);
        Spinner spinnerDoresCabecaEdit = findViewById(R.id.spinnerDoresCabecaEdit);
        Spinner spinnerDoresMuscularesEdit = findViewById(R.id.spinnerDoresMuscularesEdit);
        Spinner spinnerCansacoEdit = findViewById(R.id.spinnerCansacoEdit);
        Spinner spinnerDoresGargantaEdit = findViewById(R.id.spinnerDoresGargantaEdit);
        Spinner spinnerTosseEdit = findViewById(R.id.spinnerTosseEdit);
        TextInputEditText mensagemTemperaturaEdit = (TextInputEditText) findViewById(R.id.editTextTemperatura);
        Spinner spinnerRespiracaoEdit = findViewById(R.id.spinnerRespiracaoEdit);
        Spinner spinnerCorrimentoNasalEdit = findViewById(R.id.spinnerCorrimentoNasalEdit);

        //mete os valores em strings
        String data = mensagemDataEdit.getText().toString();
        String doresCabeca = spinnerDoresCabecaEdit.getSelectedItem().toString();
        String doresMusculares = spinnerDoresMuscularesEdit.getSelectedItem().toString();
        String cansaco = spinnerCansacoEdit.getSelectedItem().toString();
        String doresGarganta = spinnerDoresGargantaEdit.getSelectedItem().toString();
        String tosse = spinnerTosseEdit.getSelectedItem().toString();
        String temperatura = mensagemTemperaturaEdit.getText().toString();
        String respiracao = spinnerRespiracaoEdit.getSelectedItem().toString();
        String corrimentoNasal = spinnerCorrimentoNasalEdit.getSelectedItem().toString();
        long idPessoa = Nomespinner.getSelectedItemId();
        String pessoa = Nomespinner.getSelectedItem().toString();

        Float temperaturaFloat = Float.parseFloat(temperatura);

        //validação
        if(data.trim().isEmpty()){
            mensagemDataEdit.setError(getString(R.string.obrigatorio));
            mensagemDataEdit.requestFocus();
            return;
        }
        if(temperatura.trim().isEmpty()){
            mensagemTemperaturaEdit.setError(getString(R.string.obrigatorio));
            mensagemTemperaturaEdit.requestFocus();
            return;
        }

        //guardar dados
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
            Uri enderecoSintoma = Uri.withAppendedPath(CovidContentProvider.ENDERECO_SINTOMAS, String.valueOf(sintoma.getId()));

            int registos = this.getContentResolver().update(enderecoSintoma, Converte.SintomaToContentValues(sintoma), null, null);

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