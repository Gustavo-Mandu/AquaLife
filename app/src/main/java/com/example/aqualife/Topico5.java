package com.example.aqualife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.Toast;

public class Topico5 extends AppCompatActivity {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnHome;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_layout_topico5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true) {
            int i = 0;
            @Override
            public void handleOnBackPressed() {
                i++;
                if(i == 2)
                    finish();
                else Toast.makeText(getApplicationContext(), "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });


        btnVoltar = findViewById(R.id.btnVoltar);
        btnAvancar = findViewById(R.id.btnAvancar);
        btnHome = findViewById(R.id.btnHome);

        String txtNome = getIntent().getStringExtra("editUsername");
        String txtSenha = getIntent().getStringExtra("editPassword");

        sharedPrefManager = new SharedPrefManager(this);
        sharedPrefManager.saveLastActivityId(txtNome, 10);

        btnAvancar.setOnClickListener(v -> {
            Intent intent = new Intent(Topico5.this, Topico5_t1.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(Topico5.this, Topico4_zquiz5.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(Topico5.this, MainActivity.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });





       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // Alterar a cor da barra de status
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            // Alterar a cor da barra de navegação
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.blue));
        } */

    }
}