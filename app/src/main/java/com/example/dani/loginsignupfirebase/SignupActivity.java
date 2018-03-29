package com.example.dani.loginsignupfirebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class SignupActivity extends AppCompatActivity {


    private EditText email,password;
    private Button registerbtn,forgetbtn,loginbtn;
    private ProgressBar progressbar;
    private FirebaseAuth auth;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        email= findViewById(R.id.email);
        password= findViewById(R.id.password);

        registerbtn=findViewById(R.id.registerbtn);
        forgetbtn=findViewById(R.id.forgetbtn);
        loginbtn=findViewById(R.id.login);

        progressbar=findViewById(R.id.progressBar);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getemail = email.getText().toString().trim();
                String getpassword = password.getText().toString().trim();


                if (TextUtils.isEmpty(getemail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(getpassword)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }


                progressbar.setVisibility(View.VISIBLE);

                //create user

                auth.createUserWithEmailAndPassword(getemail,getpassword).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" +
                                task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressbar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." +
                                            task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignupActivity.this, Main2Activity.class));
                            finish();
                        }
                    }
                });

            }
        });

        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressbar.setVisibility(View.GONE);
    }
}

