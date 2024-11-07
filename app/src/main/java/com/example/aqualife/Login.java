package com.example.aqualife;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private Button btnLogin;
    private TextInputEditText editUsername;
    private TextInputEditText editPassword;
    //private ImageView homeLogo;
    private ProgressBar progressLogin;
    private final Timer timer = new Timer();
    private TimerTask timerTask;
    private TextView txtCadastrar;
    private View containerLogin;
    private ConstraintLayout main;
    private TextView txtSobre;

    private DatabaseHelper dbHelper;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
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
                else Toast.makeText(Login.this, "Precione mais uma vez para sair.", Toast.LENGTH_SHORT).show();
            }
        });


        btnLogin = findViewById(R.id.btnLogin);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        //homeLogo = findViewById(R.id.homeLogo);
        progressLogin = findViewById(R.id.progressLogin);
        txtCadastrar = findViewById(R.id.txtCadastrar);
        containerLogin = findViewById(R.id.containerLogin);
        main = findViewById(R.id.main);
        txtSobre = (TextView) findViewById(R.id.txtSobre); // esse (TextView) é opcional

        progressLogin.setEnabled(false);

        txtSobre.setOnClickListener(v -> {
            // Infla o layout personalizado
            LayoutInflater inflater = getLayoutInflater();
            View popupView = inflater.inflate(R.layout.popup_sobre, null);

            // Configura o conteúdo do AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(popupView);

            // Cria e exibe o diálogo
            AlertDialog dialog = builder.create();

            // Botão para fechar o diálogo
            Button closeButton = popupView.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(view -> dialog.dismiss());

            // Botão para ir no Config
            Button btnConfigGo = popupView.findViewById(R.id.btnConfigGo);
            btnConfigGo.setOnClickListener(View -> {
                Intent intent = new Intent(this, UserSettings.class);
                startActivity(intent);
                finish();
            });

            dialog.show();
        });

        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnLogin.setEnabled(!TextUtils.isEmpty(s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Não precisamos fazer nada aqui
            }
        });

       /* homeLogo.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }); */


        txtCadastrar.setOnClickListener(v -> {
            Intent intentGo = new Intent(Login.this, Cadastro.class);
            startActivity(intentGo);
            finish();
        });

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
                    constraintSet.setVerticalBias(R.id.containerLogin, 0.1f);
                } else { // Quando o teclado está oculto
                    constraintSet.setVerticalBias(R.id.containerLogin, 0.468f);
                }
                constraintSet.applyTo(main);
            }
        });



        btnLogin.setOnClickListener(v -> {

            String username = Objects.requireNonNull(editUsername.getText()).toString();
            String password = Objects.requireNonNull(editPassword.getText()).toString();

            if(dbHelper.validateUser(username, password)) {
                // Colocar mais aqui pera ai
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                String txtNome = editUsername.getText().toString();
                                String txtSenha = editPassword.getText().toString();
                                intent.putExtra("editUsername", txtNome);
                                intent.putExtra("editPassword", txtSenha);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                };
                fecharTeclado();
                if (progressLogin != null) {
                    btnLogin.setText("");
                    progressLogin.getIndeterminateDrawable().setColorFilter(
                            ContextCompat.getColor(this, R.color.white),
                            PorterDuff.Mode.SRC_IN
                    );
                }
                timer.schedule(timerTask, 1000);

            } else {
                Toast.makeText(this, "Nome de usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
            }
        });
        }
    private void fecharTeclado(){
        View view = this.getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}