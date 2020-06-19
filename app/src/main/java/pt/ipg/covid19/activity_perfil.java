package pt.ipg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class activity_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Intent intent = getIntent();
        //data Nascimento
        String data = intent.getStringExtra("covid19_data");
        TextView dataNascimento = (TextView) findViewById(R.id.textViewNascimento);
        dataNascimento.setText(data);
        //Sexo
        String sexo = intent.getStringExtra("covid19_sexo");
        TextView Sexo = (TextView) findViewById(R.id.textViewSexo);
        Sexo.setText(sexo);
        //altura
        String altura = intent.getStringExtra("covid19_altura");
        TextView AlturaText = (TextView) findViewById(R.id.textViewAltura);
        AlturaText.setText(altura);
        //Sangue
        String sangue = intent.getStringExtra("covid19_sangue");
        TextView TipoSangue = (TextView) findViewById(R.id.textViewTipoSangue);
        TipoSangue.setText(sangue);
    }

    public void onClickEditarPerfil(View view){
        Intent IntentEditarPerfil = new Intent(this, activity_perfil_editar.class);
        startActivity(IntentEditarPerfil);
    }
}
