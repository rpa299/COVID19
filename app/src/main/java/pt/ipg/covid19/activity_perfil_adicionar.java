package pt.ipg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.Calendar;

public class activity_perfil_adicionar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView dataNascimentoAdd;
    TextInputEditText editTextAlturaAdd;
    TextInputEditText editTextPesoAdd;
    TextInputEditText editTextNomeAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_adicionar);

        editTextNomeAdd = (TextInputEditText) findViewById(R.id.editTextNomePerfilAdd);
        editTextAlturaAdd =(TextInputEditText) findViewById(R.id.editTextAlturaAddPerfil);
        editTextPesoAdd = (TextInputEditText) findViewById(R.id.editTextPesoAddPerfil);

        Spinner spinner = findViewById(R.id.spinnerSexoAdd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.SexoPerfil,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinnerSangue = findViewById(R.id.spinnerTipoSagueAdd);
        ArrayAdapter<CharSequence> adapterSangue = ArrayAdapter.createFromResource(this,R.array.SanguePerfil,android.R.layout.simple_spinner_item);
        adapterSangue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSangue.setAdapter(adapterSangue);
        spinnerSangue.setOnItemSelectedListener(this);
    }

    public void escolherData(View view){
        dataNascimentoAdd = findViewById(R.id.textViewDataNascimentoPerfilAdd);

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

                dataNascimentoAdd.setText(dateCharSequence);

            }
        }, YEAR,MONTH,DATE);
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void GuardarPerfil(View view){
        validarCampos();
    }

    public void validarCampos(){
        TextView mensagemNomeAdd = (TextInputEditText) findViewById(R.id.editTextNomePerfilAdd);
        TextView mensagemDataAdd = (TextView) findViewById(R.id.textViewDataNascimentoPerfilAdd);
        Spinner spinnerSexo = findViewById(R.id.spinnerSexoAdd);
        TextInputEditText mensagemAlturaAdd = (TextInputEditText) findViewById(R.id.editTextAlturaAddPerfil);
        TextInputEditText mensagemPesoAdd = (TextInputEditText) findViewById(R.id.editTextPesoAddPerfil);
        Spinner spinnerSangue = findViewById(R.id.spinnerTipoSagueAdd);

        //mete os valores em strings
        String nome =mensagemNomeAdd.getText().toString();
        String data = mensagemDataAdd.getText().toString();
        String sexo = spinnerSexo.getSelectedItem().toString();
        String altura = mensagemAlturaAdd.getText().toString();
        String peso = mensagemPesoAdd.getText().toString();
        String sangue = spinnerSangue.getSelectedItem().toString();

        Integer alturaInt = Integer.parseInt(altura);
        Integer pesoFloat = Integer.parseInt(peso);

        //validação
        if(nome.trim().isEmpty()){
            mensagemNomeAdd.setError(getString(R.string.obrigatorio));
            mensagemNomeAdd.requestFocus();
            return;
        }
        if(altura.trim().isEmpty()){
            mensagemAlturaAdd.setError(getString(R.string.obrigatorio));
            mensagemAlturaAdd.requestFocus();
            return;
        }
        if(peso.trim().isEmpty()){
            mensagemPesoAdd.setError(getString(R.string.obrigatorio));
            mensagemPesoAdd.requestFocus();
            return;
        }

        //guardar dados
        Perfil perfil = new Perfil();
        perfil.setNome(nome);
        perfil.setDataNascimento(data);
        perfil.setSexo(sexo);
        perfil.setAltura(alturaInt);
        perfil.setPeso(pesoFloat);
        perfil.setTipoSangue(sangue);

        try {
            this.getContentResolver().insert(CovidContentProvider.ENDERECO_PERFIL, Converte.PerfilToContentValues(perfil));
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
            finish();

        } catch (Exception e) {
            Snackbar.make(editTextNomeAdd, "Erro: Não foi possível criar a Pessoa", Snackbar.LENGTH_INDEFINITE).show();
        }
    }

    public void cancelar(View view){
        finish();
        Toast.makeText(this, ("Cancelado"),Toast.LENGTH_LONG).show();
    }
}