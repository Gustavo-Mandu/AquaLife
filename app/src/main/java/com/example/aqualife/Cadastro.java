package com.example.aqualife;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Cadastro extends AppCompatActivity {


    private Button btnCadastro;
    private TextInputEditText editUsername;
    private TextInputEditText editPassword;
    //private ImageView homeLogo;
    private ProgressBar progressCadastro;
    private final Timer timer = new Timer();
    private TimerTask timerTask;
    private TextView txtLogar;
    private View containerCadastro;
    private ConstraintLayout main;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
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
                else Toast.makeText(Cadastro.this, "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });

        txtLogar = findViewById(R.id.txtLogar);
        btnCadastro = findViewById(R.id.btnCadastro);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        //homeLogo = findViewById(R.id.homeLogo);
        progressCadastro = findViewById(R.id.progressCadastro);
        containerCadastro = findViewById(R.id.containerCadastro);
        main = findViewById(R.id.main);

        progressCadastro.setEnabled(false);


        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnCadastro.setEnabled(!TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Não precisamos fazer nada aqui
            }
        });

       /* homeLogo.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, MainActivity.class);
            startActivity(intent);
        }); */

        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Verifica o tamanho do teclado
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int screenHeight = main.getRootView().getHeight();
                int keypadHeight = screenHeight - rect.bottom;

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(main);

                if (keypadHeight > screenHeight * 0.15) { // Quando o teclado está visível
                    constraintSet.setVerticalBias(R.id.containerCadastro, 0.1f);
                } else { // Quando o teclado está oculto
                    constraintSet.setVerticalBias(R.id.containerCadastro, 0.468f);
                }
                constraintSet.applyTo(main);
            }
        });

        txtLogar.setOnClickListener(v -> {
            Intent intent = new Intent(Cadastro.this, Login.class);
            startActivity(intent);
            finish();
        });

        btnCadastro.setOnClickListener(v -> {

            String username = editUsername.getText().toString().trim();
            String password =(editPassword.getText()).toString().trim();

            if (!username.isEmpty() && !password.isEmpty()){
                boolean isInserted = dbHelper.insertUser(username, password);
                if (isInserted) {
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressCadastro.getIndeterminateDrawable().setColorFilter(
                                            ContextCompat.getColor(Cadastro.this, R.color.sem_cor),
                                            PorterDuff.Mode.SRC_IN);
                                    btnCadastro.setText("Cadastro realizado!");
                                }
                            });
                        }
                    };
                    fecharTeclado();
                    if (progressCadastro != null) {
                        btnCadastro.setText("");
                        progressCadastro.getIndeterminateDrawable().setColorFilter(
                                ContextCompat.getColor(this, R.color.white),
                                PorterDuff.Mode.SRC_IN
                        );
                    }
                    timer.schedule(timerTask, 1000);
                } else {
                    Toast.makeText(this, "Usuário já existe!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            }






        });
    }
    private void fecharTeclado(){
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}