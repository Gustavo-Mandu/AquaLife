package com.example.aqualife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private TextView txtChangeAccount;
    private Button btnStart, btnContinue;
    private TextView txtUsername;
    private DatabaseHelper dbHelper;
    private String username, password;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
                if(i == 2){
                    finish();
                }
                else Toast.makeText(MainActivity.this, "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });


        txtChangeAccount = findViewById(R.id.txtChangeAccount);
        btnStart = findViewById(R.id.btnStart);
        txtUsername = findViewById(R.id.txtUsername);
        btnContinue = findViewById(R.id.btnContinue);

        String txtNome = getIntent().getStringExtra("editUsername");
        String txtSenha = getIntent().getStringExtra("editPassword");
        txtUsername.setText("Olá, " + txtNome);


        txtChangeAccount.setOnClickListener(v -> {
            // Da para usar getApplicationContext() no lugar de MainActivity.this
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.putExtra("editUsername", txtNome);
            intent.putExtra("editPassword", txtSenha);
            startActivity(intent);
            finish();
        });
        AtomicInteger i = new AtomicInteger();
        btnStart.setOnClickListener(v -> {

                i.getAndIncrement();
                if (i.get() == 2) {
                    Intent intent = new Intent(MainActivity.this, Topico1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Precione mais uma vez (Vai excluir o seu progresso).", Toast.LENGTH_SHORT).show();
                }


        });

        sharedPrefManager = new SharedPrefManager(this);
        btnContinue.setOnClickListener(v -> {
            int lastActivityId = sharedPrefManager.getLastActivityId(txtNome);

            Intent intent;
            switch (lastActivityId) {
                case 1:
                    intent = new Intent(MainActivity.this, Topico1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, Topico1_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, Topico1_t2.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, Topico2.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, Topico2_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, Topico3.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, Topico3_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 8:
                    intent = new Intent(MainActivity.this, Topico4.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 9:
                    intent = new Intent(MainActivity.this, Topico4_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 10:
                    intent = new Intent(MainActivity.this, Topico5.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 11:
                    intent = new Intent(MainActivity.this, Topico5_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 12:
                    intent = new Intent(MainActivity.this, Topico6.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 13:
                    intent = new Intent(MainActivity.this, Topico6_t1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                case 14:
                    intent = new Intent(MainActivity.this, MainActivity2.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
                default:
                    intent = new Intent(MainActivity.this, Topico1.class);
                    intent.putExtra("editUsername", txtNome);
                    intent.putExtra("editPassword", txtSenha);
                    break;
            }
            startActivity(intent);
            finish();
        });

    }

}