package com.example.fix_it_app.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fix_it_app.MainActivity;
import com.example.fix_it_app.R;
import com.example.fix_it_app.Utili.Connection;
import com.example.fix_it_app.Utili.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail,mPassword;
    private FirebaseAuth fAuth;
    private Connection connection;
    private Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = new Loading(this);
        connection = new Connection(getApplicationContext());
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        Button mLoginBtn = findViewById(R.id.loginBtn);
        TextView mRegBtn = findViewById(R.id.createText);
        TextView resetPass = findViewById(R.id.resetPassword);

        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(!checkLogin(email, password)){
                    return;
                }

                if(!connection.checkConnection()){
                    Toast.makeText(LoginActivity.this, "No internet connection, check your settings", Toast.LENGTH_SHORT).show();
                    return;
                }

                //controllo se l'utente è registrato
                loading.loadingAlertDialog();
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            loading.dismissDialog();
                            if(checkEmailVerification())
                                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmail.getText().toString().isEmpty()){
                    mEmail.setError("Email Required.");
                    mEmail.requestFocus();
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(mEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(LoginActivity.this, "Check your email for reset password", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(LoginActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    private boolean checkLogin(String email, String password) {
        if(email.isEmpty()){
            mEmail.setError("Email Required.");
            mEmail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Vali Email Required");
            mEmail.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            mPassword.setError("Password is Required.");
            mPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(fAuth.getCurrentUser() != null && checkEmailVerification())
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    public Boolean checkEmailVerification(){
        FirebaseUser firebaseUser = fAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            return true;
        }
        else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            return false;
        }
    }

}
