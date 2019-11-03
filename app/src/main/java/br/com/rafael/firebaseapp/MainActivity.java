package br.com.rafael.firebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore firebaseFirestore;
    //
    private EditText etDescricao;
    private EditText etAno;
    private Button btSalvar;
    private Button btConsultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        inicializaComponentes();
        iniciaFirestore();
    }

    private void iniciaFirestore() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void inicializaComponentes() {
        etAno = findViewById(R.id.etAno);
        etDescricao = findViewById(R.id.etDescricao);
        btSalvar = findViewById(R.id.btSalvar);
        btConsultar = findViewById(R.id.btConsultar);
        //
        btSalvar.setOnClickListener(this);
        btConsultar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSalvar:
                salvaInformacao();
                break;
            case R.id.btConsultar:
                abreTelaConsulta();
                break;
        }
    }

    private void salvaInformacao() {
        Universidade universidade = new Universidade();
        universidade.setAnoFundacao(Long.parseLong(etAno.getText().toString()));
        universidade.setDescricao(etDescricao.getText().toString());
        firebaseFirestore
                .collection("UNIVERSIDADES")
                .add(universidade);
        //
        etAno.setText("");
        etDescricao.setText("");
        etDescricao.requestFocus();
    }

    private void abreTelaConsulta() {
        Intent intent = new Intent(this, ListaUniversidadesActivity.class);
        startActivity(intent);
    }
}
