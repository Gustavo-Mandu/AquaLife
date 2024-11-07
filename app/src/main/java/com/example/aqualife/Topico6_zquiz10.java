package com.example.aqualife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Topico6_zquiz10 extends AppCompatActivity {

    private RadioButton rdioButton1, rdioButton2, rdioButton3, rdioButton4;
    private Button btnCheck, btnContinue, btnVoltar;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout_topico6_zquiz10);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rdioButton1 = findViewById(R.id.rdioButton1);
        rdioButton2 = findViewById(R.id.rdioButton2);
        rdioButton3 = findViewById(R.id.rdioButton3);
        rdioButton4 = findViewById(R.id.rdioButton4);
        btnCheck = findViewById(R.id.btnCheck);
        btnContinue = findViewById(R.id.btnContinue);
        btnVoltar = findViewById(R.id.btnVoltar);

        String txtNome = getIntent().getStringExtra("editUsername");
        String txtSenha = getIntent().getStringExtra("editPassword");

        btnCheck.setOnClickListener(v -> {
            if (rdioButton1.isChecked()){
                rdioButton1.setBackgroundResource(R.drawable.container_radiobutton_incorrect);
                btnCheck.setText("Errou!");
                btnCheck.setEnabled(false);
                btnCheck.setBackgroundResource(R.drawable.background_button_enabled);
                rdioButton4.setBackgroundResource(R.drawable.container_radiobutton_correct);
            } else
            if (rdioButton2.isChecked()){
                rdioButton2.setBackgroundResource(R.drawable.container_radiobutton_incorrect);
                btnCheck.setText("Errou!");
                btnCheck.setEnabled(false);
                btnCheck.setBackgroundResource(R.drawable.background_button_enabled);
                rdioButton4.setBackgroundResource(R.drawable.container_radiobutton_correct);
            } else
            if (rdioButton3.isChecked()) {
                rdioButton3.setBackgroundResource(R.drawable.container_radiobutton_incorrect);
                btnCheck.setText("Errou!");
                btnCheck.setEnabled(false);
                btnCheck.setBackgroundResource(R.drawable.background_button_enabled);
                rdioButton4.setBackgroundResource(R.drawable.container_radiobutton_correct);
            } else
            if (rdioButton4.isChecked()) {
                rdioButton4.setBackgroundResource(R.drawable.container_radiobutton_correct);
                btnCheck.setText("Acertou!");
                btnCheck.setEnabled(false);
                btnCheck.setBackgroundResource(R.drawable.background_button_enabled);
            }
        });

        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Topico6_zquiz9.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

    }

    public boolean respostaCorreta(View v) {
        return rdioButton1.isChecked();
    }
}