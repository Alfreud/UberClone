package com.alfred0ga.uberclone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.activities.client.RegisterActivity;
import com.alfred0ga.uberclone.activities.driver.RegisterDriverActivity;
import com.alfred0ga.uberclone.includes.MyToolbar;

public class SelectOptionsAuthActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_options_auth);

        MyToolbar.show(this, "Seleccionar opción", true);

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(v -> goToLogin());
        btnRegister.setOnClickListener(v -> goToRegister());
    }

    private void goToLogin() {
        Intent intent = new Intent(SelectOptionsAuthActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToRegister(){
        String typeUser = mPreferences.getString("user", "");
        if (typeUser.equals("client")){
            Intent intent = new Intent(SelectOptionsAuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SelectOptionsAuthActivity.this, RegisterDriverActivity.class);
            startActivity(intent);
        }
    }
}