package com.example.testppe.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testppe.BDD.DBHelper;
import com.example.testppe.BDD.DBHelper_Avis;
import com.example.testppe.BDD.DBHelper_Utilisateur;
import com.example.testppe.MainActivity;
import com.example.testppe.R;
import com.example.testppe.SQL_Utilisateur;
import com.example.testppe.ui.login.LoginViewModel;
import com.example.testppe.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private SQL_Utilisateur BDD = null;
    private int util_id = 0;
    private DBHelper_Utilisateur help;
    private DBHelper help1;
    private DBHelper_Avis help2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        BDD = new SQL_Utilisateur();
        help = new DBHelper_Utilisateur(LoginActivity.this.getBaseContext());
        help1 = new DBHelper(LoginActivity.this.getBaseContext());
        help2 = new DBHelper_Avis(LoginActivity.this.getBaseContext());


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button registerButton = findViewById(R.id.button_register);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                registerButton.setEnabled(loginFormState.isDataValid());

                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                loadingProgressBar.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, usernameEditText.getText().toString(), Toast.LENGTH_LONG).show();
                /*Thread background = new Thread(new Runnable() {
                    public void run() {*/

                        if (help.getData(usernameEditText.getText().toString()).equals(passwordEditText.getText().toString()))
                        {
                                //BDD.verification(LoginActivity.this, usernameEditText.getText().toString(), passwordEditText.getText().toString()).equals(passwordEditText.getText().toString())) {
                            util_id=help.getid( usernameEditText.getText().toString());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("EXTRA_SESSION_ID", 0);
                            startActivity(intent);
                        }else {
                           /* LoginActivity.this.runOnUiThread(new Runnable()
                               {
                                public void run() {*/Toast.makeText(LoginActivity.this, "WRONG MAIL OR PASSWORD. PLEASE TRY AGAIN",Toast.LENGTH_LONG).show();
                               // }});
                        }
                   /* }});
                background.start();*/


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(LoginActivity.this, Register.class);
                startActivity(intent1);

            }
        });

    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}