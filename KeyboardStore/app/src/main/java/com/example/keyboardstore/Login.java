/*
Sources:
https://firebase.google.com/docs/auth/android/google-signin
https://firebase.google.com/docs/auth/android/start/
https://developer.android.com/training/snackbar/showing
https://firebase.google.com/docs/auth/android/password-auth
 */
package com.example.keyboardstore;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public EditText email, password;
    public Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        register.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), Register.class);
            startActivity(i);
        });
}

    // Checking current auth state
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
        }
    }

    private void signIn() {
        String emailString = email.getText().toString().trim();
        String passString = password.getText().toString().trim();

        if(emailString.isEmpty()) {
            Snackbar.make(findViewById(R.id.loginLayout), R.string.enter_email,
                    Snackbar.LENGTH_SHORT)
                    .show();
            }
            else if(passString.isEmpty()) {
                Snackbar.make(findViewById(R.id.loginLayout), R.string.enter_pass,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
            else { mAuth.signInWithEmailAndPassword(emailString, passString)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.loginLayout), R.string.error,
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });


        }

    }
}
