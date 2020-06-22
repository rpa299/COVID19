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

import java.util.Calendar;

public class activity_sintomas_adicionar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    public void Guardar(View view){

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
}
