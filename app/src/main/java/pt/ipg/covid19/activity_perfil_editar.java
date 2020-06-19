package pt.ipg.covid19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;

public class activity_perfil_editar extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Object> {
    private static final int ID_CURSOR_LOADER_SERVICO = 0;
    TextView dataNascimento;
    TextInputEditText editTextAlturaEditar;
    TextInputEditText editTextPesoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_SERVICO, null, this);
    }
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_SERVICO, null, this);

        super.onResume();
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

    public void onClickPerfilEditar(View view){
        Intent IntentPerfilEditar = new Intent(this, activity_perfil_editar.class);
        startActivity(IntentPerfilEditar);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @NonNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {

    }

    class SpinnerSexoPerfil implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String sexo = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class SpinnerSanguePerfil implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String sangue = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void validarCampos(){
        TextView mensagemDataNascimento = (TextView) findViewById(R.id.textViewDataNascimento);
        TextInputEditText mensagemAlturaEditar = (TextInputEditText) findViewById(R.id.editTextAlturaEditar);
        TextInputEditText mensagemPesoEditar = (TextInputEditText) findViewById(R.id.editTextPesoEditar);

        String data =mensagemDataNascimento.getText().toString();
        String altura = mensagemAlturaEditar.getText().toString();
        String peso = mensagemPesoEditar.getText().toString();

        if(data.trim().length() == 0){
            mensagemDataNascimento.setError(getString(R.string.obrigatorio));
            mensagemDataNascimento.requestFocus();
            return;
        }
        if(altura.trim().length() == 0){
            mensagemAlturaEditar.setError(getString(R.string.obrigatorio));
            mensagemAlturaEditar.requestFocus();
            return;
        }
        if(peso.trim().length() == 0){
            mensagemPesoEditar.setError(getString(R.string.obrigatorio));
            mensagemPesoEditar.requestFocus();
            return;
        }
    }

    public void Guardar(){
        validarCampos();
    }
}