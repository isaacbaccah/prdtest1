package com.prdtest0.prdtest1;

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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText Emailtext;
    private EditText Passwordtext;
    private Button Loginbtn;
    private Button notRegbtn;

    private FirebaseAuth mAuth;
    private ProgressBar bprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        Emailtext = (EditText) findViewById(R.id.reg_email);
        Passwordtext = (EditText)  findViewById(R.id.reg_confirm_pass);
        bprogress = (ProgressBar) findViewById(R.id.login_progress);

        Loginbtn = (Button) findViewById(R.id.btnLogin);
        notRegbtn = (Button) findViewById(R.id.btnReg);

        notRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);

            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String loginEmail = Emailtext.getText().toString();
                String loginPass = Passwordtext.getText().toString();

                    if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
                        bprogress.setVisibility(View.VISIBLE);

                        mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    sendtoMain();

                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(LoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                                }

                                bprogress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){

            sendtoMain();


        }

    }

    private void sendtoMain() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
