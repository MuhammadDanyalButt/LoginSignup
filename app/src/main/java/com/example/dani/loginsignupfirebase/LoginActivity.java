package com.example.dani.loginsignupfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private  Button login;
    private Button signup;
    private Button forget;
    private EditText email,password;
    private ProgressBar progressBar;


    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, Main2Activity.class));
            finish();
        }

        // set the view now
        setContentView(R.layout.activity_login);

      //  Toolbar toolbar =  findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);


        login = findViewById(R.id.btn_login);
        signup=findViewById(R.id.btn_signup);
        forget=findViewById(R.id.btn_forget_password);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String getemail = email.getText().toString();
                final String getpassword = password.getText().toString();

                if (TextUtils.isEmpty(getemail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(getpassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                //authenticate user
                auth.signInWithEmailAndPassword(getemail,getpassword).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                            // there was an error
                            if (getpassword.length() < 6) {
                                password.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        });
    }
}
