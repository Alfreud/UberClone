package com.alfred0ga.uberclone.activities.client;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alfred0ga.uberclone.R;
import com.alfred0ga.uberclone.activities.driver.MapDriverActivity;
import com.alfred0ga.uberclone.activities.driver.RegisterDriverActivity;
import com.alfred0ga.uberclone.includes.MyToolbar;
import com.alfred0ga.uberclone.models.Client;
import com.alfred0ga.uberclone.providers.AuthProvider;
import com.alfred0ga.uberclone.providers.ClientProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etPass;
    private AlertDialog mDialog;

    AuthProvider mAuthProvider;
    ClientProvider mClientProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        MyToolbar.show(this, "Registro de usuario", true);

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);
        btnRegister = findViewById(R.id.btn_register);

        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .build();

        btnRegister.setOnClickListener(v -> clickRegister());
    }

    private void clickRegister() {
        final String name = etName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String pass = etPass.getText().toString().trim();

        if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty()) {
            if (pass.length() >= 6) {
                mDialog.show();
                register(name, email, pass);
            } else {
                Toast.makeText(this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(final String name, String email, String pass) {
        mAuthProvider.register(email, pass).addOnCompleteListener(task -> {
            mDialog.hide();
            if (task.isSuccessful()) {
                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Client client = new Client(id, name, email);
                create(client);
            } else {
                Toast.makeText(this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void create(Client client){
        mClientProvider.create(client).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                //Toast.makeText(this, "El registro se realizo exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, MapClientActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No se pudo crear el cliente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void saveUser(String id, String name, String email) {
        String selectedUser = mPreferences.getString("user", "");
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        if (selectedUser.equals("driver")) {
            mReference.child("Users").child("Drivers").child(id).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (selectedUser.equals("client")) {
            mReference.child("Users").child("Clients").child(id).setValue(user).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Fallo el registro", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/
}