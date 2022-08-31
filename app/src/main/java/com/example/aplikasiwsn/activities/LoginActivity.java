package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;
import com.example.aplikasiwsn.connections.configs.AppAPI;
import com.example.aplikasiwsn.models.User;
import com.example.aplikasiwsn.services.LoginService;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    CredentialSharedPreferences cred;
    private User userNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cred = new CredentialSharedPreferences(this);
        this.etUsername = findViewById(R.id.et_username);
        this.etPassword = findViewById(R.id.et_password);
        this.btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                LoginService loginService = AppAPI.getRetrofit().create(LoginService.class);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setCancelable(false); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
                progressDialog.show(); // show progress dialog

                loginService.loginRequest(username, password).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismiss();
                        userNow = response.body();

                        cred.saveUsername(userNow.getUsername());
                        cred.saveToken(userNow.getToken());
                        cred.saveLoginDate();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss(); //dismiss progress dialog
                    }
                });
            }

        });
    }
}
