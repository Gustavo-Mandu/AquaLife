package com.example.aqualife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Topico1_t1 extends AppCompatActivity {

    private Button btnVoltar, btnAvancar, btnHome;
    private DatabaseHelper dbHelper;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout_topico1_t1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            int i = 0;
            @Override
            public void handleOnBackPressed() {
                i++;
                if(i == 2)
                    finish();
                else Toast.makeText(Topico1_t1.this, "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });

        btnVoltar = findViewById(R.id.btnVoltar);
        btnAvancar = findViewById(R.id.btnAvancar);
        btnHome = findViewById(R.id.btnHome);

        String txtNome = getIntent().getStringExtra("editUsername");
        String txtSenha = getIntent().getStringExtra("editPassword");

        sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.saveLastActivityId(txtNome, 2);

        btnAvancar.setOnClickListener(v -> {

            Intent intent = new Intent(Topico1_t1.this, Topico1_t2.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Topico1_t1.this, Topico1.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(Topico1_t1.this, MainActivity.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });
    }
}