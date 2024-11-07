package com.example.aqualife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class UserSettings extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private AppCompatButton btnDeleteUser;
    private TextInputEditText editUsername;
    private TextInputEditText editPassword;
    private AppCompatButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_settings);
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
                else Toast.makeText(UserSettings.this, "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });

        dbHelper = new DatabaseHelper(this);

        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnHome = findViewById(R.id.btnHome);

        btnDeleteUser.setOnClickListener(v -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            boolean isDeleted = dbHelper.deleteUser(username, password);
            if (isDeleted) {
                Toast.makeText(this, "Usuário deletado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao deletar o usuário", Toast.LENGTH_SHORT).show();
            }
        });

        /* btnDeleteAllUsers.setOnClickListener(v -> {
            boolean isDeleted = dbHelper.deleteAllUsers();
            if (isDeleted) {
                Toast.makeText(this, "Todos os Usuários deletados com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao deletar todos os usuários", Toast.LENGTH_SHORT).show();
            }
        }); */

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        });
    }

    }
