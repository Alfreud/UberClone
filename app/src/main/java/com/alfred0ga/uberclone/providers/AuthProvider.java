package com.alfred0ga.uberclone.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthProvider {
    private FirebaseAuth mAuth;

    public AuthProvider() {
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> register(String email, String pass) {
        return mAuth.createUserWithEmailAndPassword(email, pass);
    }

    public Task<AuthResult> login(String email, String pass) {
        return mAuth.signInWithEmailAndPassword(email, pass);
    }

    public void logout(){
        mAuth.signOut();
    }

    public String getId() {
        return mAuth.getCurrentUser().getUid();
    }

    public boolean existSesion() {
        boolean exist = false;
        if (mAuth.getCurrentUser() != null) {
            exist = true;
        }
        return exist;
    }
}
