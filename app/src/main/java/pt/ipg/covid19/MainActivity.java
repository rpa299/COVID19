package pt.ipg.covid19;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickPerfil(View view){
        Intent IntentPerfil = new Intent(this, activity_perfil.class);
        startActivity(IntentPerfil);
    }

    public void onClickSus(View view){
        Intent IntentSus = new Intent(this, activity_sus_inf_adicionar.class);
        startActivity(IntentSus);
    }

    public void onClickSintomas(View view){
        Intent IntentSintomas = new Intent(this, activity_sintomas_adicionar.class);
        startActivity(IntentSintomas);
    }
}
