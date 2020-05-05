package com.example.fix_it_app.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fix_it_app.MainActivity;
import com.example.fix_it_app.Model.Users;
import com.example.fix_it_app.R;
import com.example.fix_it_app.Utili.Connection;
import com.example.fix_it_app.Utili.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText mFullName,mSurname,mFiscalCode,mEmail,mPassword, mPassowrd2, mPhone, mDocument;
    private TextView mBirthDate;
    private FirebaseAuth fAuth;
    private DatabaseReference myRef;
    private Connection connection;
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullName   = findViewById(R.id.name);
        mSurname    = findViewById(R.id.surname);
        mBirthDate  = findViewById(R.id.birthDate);
        mFiscalCode = findViewById(R.id.fiscalCode);
        mEmail      = findViewById(R.id.email);
        mPhone      = findViewById(R.id.cellNumber);
        mDocument   = findViewById(R.id.documentID);
        mPassword   = findViewById(R.id.password);
        mPassowrd2  = findViewById(R.id.password2);
        Button mRegisterBtn = findViewById(R.id.registerBtn);
        TextView mLoginBtn = findViewById(R.id.createText);

        loading = new Loading(this);
        connection = new Connection(getApplicationContext());

        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //salvo in variabili quello che scrivo sullo schermo
                final String name = mFullName.getText().toString().trim();
                final String surname = mSurname.getText().toString().trim();
                final String fiscalCode = mFiscalCode.getText().toString().trim();
                final String email = mEmail.getText().toString().trim();
                final String phone   = mPhone.getText().toString();
                final String document = mDocument.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String password2 = mPassowrd2.getText().toString().trim();


                //check dei campi
                if(!checkRegistration(name, surname, mBirthDate.getText().toString(), fiscalCode, email, phone, password, password2)){
                    return;
                }

                if(!connection.checkConnection()){
                    Toast.makeText(RegisterActivity.this, "No internet connection, check your settings", Toast.LENGTH_SHORT).show();
                    return;
                }

                loading.loadingAlertDialog();
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            loading.dismissDialog();
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            String id = myRef.push().getKey();
                            Users users = new Users(name, surname, mBirthDate.getText().toString(), fiscalCode, email, phone, document);
                            myRef.child(id).setValue(users);

                            sendEmailVerification();
                        }else
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        mBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

    }


    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = " " + dayOfMonth + "/" + month + "/" + year;
        mBirthDate.setText(date);
    }

    private boolean checkRegistration(String name, String surname, String birthDate, String fiscalCode, String email, String phone, String password, String password2) {
        Pattern patternName = Pattern.compile("^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$");
        Pattern patternPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]*$");

        if(name.isEmpty() ||  (name.length() < 2 || name.length() >=30)) {
            mFullName.setError("Inserire nome valido (Deve iniziare con una lettere maiuscola e può contenere lettere e spazi");
            mFullName.requestFocus();
            return false;
        }

        if(surname.isEmpty() ||  (surname.length() < 2 || surname.length() >=30)) {
            mFullName.setError("Inserire cognome valido (Deve iniziare con una lettere maiuscola e può contenere lettere e spazi");
            mFullName.requestFocus();
            return false;
        }


        if(fiscalCode.isEmpty() /*|| fiscalCode.length() < 16*/){
            mFiscalCode.setError("Insert a valid fiscal code");
            mFiscalCode.requestFocus();
            return false;
        }

        if(birthDate.isEmpty()){
            mBirthDate.setError("Insert date");
            mBirthDate.requestFocus();
            return false;
        }


        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Inserire mail valida");
            mEmail.requestFocus();
            return false;
        }

        if(phone.isEmpty()){
            mPhone.setError("Insert phone number");
            mPhone.requestFocus();
            return false;
        }

        if(password.isEmpty() /*|| !patternPassword.pattern(patternPassword)*/ || password.length() < 8){
            mPassword.setError("Insert password valid\n" +
                    "Almost one lowercase letter, uppercase letter and special character (@$!%*?&). Min lenght 8 charachters");
            mPassword.requestFocus();
            return false;
        }

        if(!password.equals(password2)){
            mPassowrd2.setError("Password must be equals");
            mPassowrd2.requestFocus();
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

        if(fAuth.getCurrentUser() != null)
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }



    private void sendEmailVerification(){
        FirebaseUser firebaseUser = fAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration completed, verify your email and Login", Toast.LENGTH_SHORT).show();
                        fAuth.signOut();
                        finish();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                    else
                        Toast.makeText(RegisterActivity.this, "Verification mail hasn't been sent!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
