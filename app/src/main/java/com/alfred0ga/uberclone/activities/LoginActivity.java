package com.alfred0ga.uberclone.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.activities.client.MapClientActivity;
import com.alfred0ga.uberclone.activities.driver.MapDriverActivity;
import com.alfred0ga.uberclone.includes.MyToolbar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail;
    private TextInputEditText etPass;
    private Button btnLogin;
    private CircleImageView mCircleImageBack;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    private AlertDialog mDialog;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        btnLogin = findViewById(R.id.btn_login);
        mCircleImageBack = findViewById(R.id.civ_back);

        mPreferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .build();

        btnLogin.setOnClickListener(v -> login());

        mCircleImageBack.setOnClickListener(v -> finish());
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();

        if (!email.isEmpty() && !pass.isEmpty()){
            if (pass.length() >= 6){
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user  =  mPreferences.getString("user", "");
                            if (user.equals("client")) {
                                Intent intent = new Intent(LoginActivity.this, MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MapDriverActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "El Usuario o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss();
                    }
                });
            }

            else {
                Toast.makeText(this, "La contraseña debe de tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            Toast.makeText(this, "El usuario y la contraseña son campos obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
}