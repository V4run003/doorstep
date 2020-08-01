package com.teamnightcoders.doorstep.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private EditText email, Psswd1, Psswd2;
    Button regbtn;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.mail);
        Psswd1 = findViewById(R.id.password);
        Psswd2 = findViewById(R.id.password2);
        regbtn = findViewById(R.id.regbtn);
        pd = new ProgressDialog(this);
        pd.setTitle("Please wait..");
        pd.setCancelable(false);


        firebaseAuth = FirebaseAuth.getInstance();
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg();
                pd.show();
            }
        });


    }

    private void reg() {
        String mail = email.getText().toString();
        String pswd = Psswd1.getText().toString();
        String pswd2 = Psswd2.getText().toString();


        if (mail.isEmpty()) {
            email.setError("Please enter the E-mail id");
            email.requestFocus();
        } else if (pswd.isEmpty()) {
            Psswd1.setError("Please enter the Password");
            Psswd1.requestFocus();
        } else if (pswd.length() < 8) {
            Psswd1.setError("Password must be at least 8 characters");
            Psswd1.requestFocus();
        } else if (pswd2.isEmpty()) {
            Psswd2.setError("Enter Password again");
            Psswd2.requestFocus();
        } else if (!pswd.equals(pswd2)) {
            Psswd1.setError("Passwords mismatch");
            Psswd1.requestFocus();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(mail, pswd)
                    .addOnCompleteListener((Activity) Objects.requireNonNull(getApplicationContext()), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.cancel();
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                assert user != null;
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                            } else {
                                pd.cancel();
                                Toast.makeText(RegisterActivity.this, "error", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }


}