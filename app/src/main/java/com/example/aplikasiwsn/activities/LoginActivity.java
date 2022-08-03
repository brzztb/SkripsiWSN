package com.example.aplikasiwsn.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    CredentialSharedPreferences cred;

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
                String token = "KJHkggKdhafsfJKFhdafdhhfkdas";
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                cred.saveUsername(username);
                cred.saveLoginDate();
                cred.saveToken(token);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setCancelable(false); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
//                progressDialog.show(); // show progress dialog
            }

//            Intent intent = new Intent(LoginActivity.this);
        });
    }
}
