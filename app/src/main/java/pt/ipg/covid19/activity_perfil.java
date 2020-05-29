package pt.ipg.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }

    public void onClickEditarPerfil(View view){
        Intent IntentEditarPerfil = new Intent(this, activity_perfil_editar.class);
        startActivity(IntentEditarPerfil);
    }
}
