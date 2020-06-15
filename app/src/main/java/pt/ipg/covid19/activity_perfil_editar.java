package pt.ipg.covid19;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;

public class activity_perfil_editar extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextView dataNascimento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_editar);

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
}