package com.teamnightcoders.doorstep.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import static com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "FacebookAuthentication";
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    Button verify;
    EditText email,password;
    Button regbtn,login,phone;
    ProgressDialog progress,progressgoogle;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verify=findViewById(R.id.sign_in);
        mAuth=FirebaseAuth.getInstance();

        regbtn=findViewById(R.id.regstrbtn);

        createRequest();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        intoHome();
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.text_password);
        login = findViewById(R.id.login);
        phone=findViewById(R.id.phone);
        progressgoogle = new ProgressDialog(this);
        progressgoogle.setCancelable(false);
        progressgoogle.setTitle("");
        progressgoogle.setMessage("Signing in..");
        firebaseAuth=FirebaseAuth.getInstance();
        phoneauth();
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setTitle("");
        progress.setMessage("Signing in..");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pwd = password.getText().toString();
                if (mail.isEmpty()){
                    email.setError("Please enter a valid E-mail id");
                    email.requestFocus();
                } else
                if (pwd.isEmpty()){
                    password.setError("Please enter a valid Password");
                    email.requestFocus();
                }
                else if (mail.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();

                }
                else if (!(mail.isEmpty() && pwd.isEmpty())){
                    progress.show();
                    firebaseAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                progress.cancel();
                                Toast.makeText(LoginActivity.this,"Login error, Please check the email and password",Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intoHome = new Intent(LoginActivity.this, MainActivity.class);
                                progress.cancel();
                                startActivity(intoHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this,"Error occured!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void intoHome() {
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void  phoneauth(){
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),PhoneActivity.class);
                startActivity(intent);

            }
        });

    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getResources().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progressgoogle.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d(TAG, "firebaseAuthWithGoogle:" + Objects.requireNonNull(account).getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class) ;
            startActivity(intent);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                progressgoogle.cancel();

                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent=new Intent(LoginActivity.this,UserActivity.class);
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                progressgoogle.cancel();


                            }


                        }

                    });


        }


    }


}
