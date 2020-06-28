package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;

public class activity_perfil_editar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int ID_CURSOR_LOADER_PERFIL = 0;
    private Uri enderecoPerfilEditar;

    TextView dataNascimento;
    TextInputEditText editTextAlturaEditar;
    TextInputEditText editTextPesoEditar;
    TextInputEditText editTextNomeEditar;
    TextView textViewDataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar);

        textViewDataNascimento = findViewById(R.id.textViewDataNascimento);
        editTextNomeEditar = (TextInputEditText) findViewById(R.id.editTextNomeEditPerfil);
        editTextAlturaEditar =(TextInputEditText) findViewById(R.id.editTextAlturaEditar);
        editTextPesoEditar = (TextInputEditText) findViewById(R.id.editTextPesoEditar);

        Spinner spinner = findViewById(R.id.spinnerSexoEditPerfil);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SexoPerfil,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinnerSangue = findViewById(R.id.spinnerTipoSangueEditPerfil);
        ArrayAdapter<CharSequence> adapterSangue = ArrayAdapter.createFromResource(this,R.array.SanguePerfil,android.R.layout.simple_spinner_item);
        adapterSangue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSangue.setAdapter(adapterSangue);
        spinnerSangue.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PERFIL, null, this);
        Intent intent = getIntent();
        Long idPerfil = intent.getLongExtra(activity_perfil.ID_PERFIL,-1);
        if(idPerfil == -1){
            Toast.makeText(this,R.string.Erro,Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        enderecoPerfilEditar = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PERFIL,String.valueOf(idPerfil));

        Cursor cursor = getContentResolver().query(enderecoPerfilEditar, BdTablePerfil.TODOS_CAMPOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,R.string.Erro, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Perfil perfil = Perfil.fromCursor(cursor);

        editTextNomeEditar.setText(perfil.getNome());
        textViewDataNascimento.setText(perfil.getDataNascimento());
        spinner.setSelection(getSpinnerSexo(spinner,perfil.getSexo()));
        editTextAlturaEditar.setText(""+perfil.getAltura());
        editTextPesoEditar.setText(""+perfil.getPeso());
        spinnerSangue.setSelection(getSpinnerSangue(spinnerSangue,perfil.getTipoSangue()));
    }

    public void GuardarPerfil(View view){
        validarCampos();
    }

    public void escolherData(View view){
        dataNascimento = findViewById(R.id.textViewDataNascimento);

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

                dataNascimento.setText(dateCharSequence);

            }
        }, YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    private int getSpinnerSexo(Spinner spinner, String sexo){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(sexo)){
                index = i;
            }
        }
        return index;
    }
    public int getSpinnerSangue(Spinner spinner, String sangue){
        int index = 0;
        for(int i = 0; i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(sangue)){
                index = i;
            }
        }
        return index;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void validarCampos(){
        TextView mensagemNomeEdit = (TextInputEditText) findViewById(R.id.editTextNomeEditPerfil);
        TextView mensagemDataEdit = (TextView) findViewById(R.id.textViewDataNascimento);
        Spinner spinnerSexoEdit = findViewById(R.id.spinnerSexoEditPerfil);
        TextInputEditText mensagemAlturaEdit = (TextInputEditText) findViewById(R.id.editTextAlturaEditar);
        TextInputEditText mensagemPesoEdit = (TextInputEditText) findViewById(R.id.editTextPesoEditar);
        Spinner spinnerSangueEdit = findViewById(R.id.spinnerTipoSangueEditPerfil);

        //mete os valores em strings
        String nome =mensagemNomeEdit.getText().toString();
        String data = mensagemDataEdit.getText().toString();
        String sexo = spinnerSexoEdit.getSelectedItem().toString();
        String altura = mensagemAlturaEdit.getText().toString();
        String peso = mensagemPesoEdit.getText().toString();
        String sangue = spinnerSangueEdit.getSelectedItem().toString();

        //validação
        if(nome.trim().isEmpty()){
            mensagemNomeEdit.setError(getString(R.string.obrigatorio));
            mensagemNomeEdit.requestFocus();
            return;
        }
        if(altura.trim().isEmpty()){
            mensagemAlturaEdit.setError(getString(R.string.obrigatorio));
            mensagemAlturaEdit.requestFocus();
            return;
        }
        if(peso.trim().isEmpty()){
            mensagemPesoEdit.setError(getString(R.string.obrigatorio));
            mensagemPesoEdit.requestFocus();
            return;
        }
        String nomeAlterado =mensagemNomeEdit.getText().toString();
        String dataAlterado = mensagemDataEdit.getText().toString();
        String sexoAlterado = spinnerSexoEdit.getSelectedItem().toString();
        Integer alturaInt = Integer.parseInt(altura);
        Float pesoFloat = Float.parseFloat(peso);
        String sangueAlterado = spinnerSangueEdit.getSelectedItem().toString();

        //guardar
        Perfil perfil = new Perfil();
        perfil.setNome(nomeAlterado);
        perfil.setDataNascimento(dataAlterado);
        perfil.setSexo(sexoAlterado);
        perfil.setAltura(alturaInt);
        perfil.setPeso(pesoFloat);
        perfil.setTipoSangue(sangueAlterado);

        try {
            getContentResolver().update( enderecoPerfilEditar, perfil.getContentValues(), null, null);

            Toast.makeText(this, (R.string.Sucesso), Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this,(R.string.Erro), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void cancelar(View view){
        finish();
        Toast.makeText(this, ("Cancelado"),Toast.LENGTH_LONG).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PERFIL, BdTablePerfil.TODOS_CAMPOS,
                null, null, BdTablePerfil.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}