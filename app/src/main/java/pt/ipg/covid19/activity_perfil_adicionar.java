package pt.ipg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

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
        TextInputEditText mensagemAlturaAdd = (TextInputEditText) findViewById(R.id.editTextAlturaAddPerfil);
        TextInputEditText mensagemPesoAdd = (TextInputEditText) findViewById(R.id.editTextPesoAddPerfil);

        //mete os valores em strings
        String nome =mensagemNomeAdd.getText().toString();
        String altura = mensagemAlturaAdd.getText().toString();
        String peso = mensagemPesoAdd.getText().toString();

        //validação
        if(nome.trim().length() == 0){
            mensagemNomeAdd.setError(getString(R.string.obrigatorio));
            mensagemNomeAdd.requestFocus();
            return;
        }
        if(altura.trim().length() == 0){
            mensagemAlturaAdd.setError(getString(R.string.obrigatorio));
            mensagemAlturaAdd.requestFocus();
            return;
        }
        if(peso.trim().length() == 0){
            mensagemPesoAdd.setError(getString(R.string.obrigatorio));
            mensagemPesoAdd.requestFocus();
            return;
        }
    }

    public void cancelar(View view){
        finish();
        Toast.makeText(this, ("Cancelado"),Toast.LENGTH_LONG).show();
    }
}