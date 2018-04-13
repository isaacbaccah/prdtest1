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

public class RegisterActivity extends AppCompatActivity {

    private EditText mRegemail;
    private EditText mRegpass;
    private EditText mRegconfirmpass;

    private Button mRegbtn;
    private Button mloginbtn;

    private ProgressBar mRegprogress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mRegemail = (EditText) findViewById(R.id.reg_email);
        mRegpass = (EditText) findViewById(R.id.reg_pass);
        mRegconfirmpass = (EditText) findViewById(R.id.reg_confirm_pass);
        mRegbtn = (Button) findViewById(R.id.reg_btn);
        mloginbtn = (Button) findViewById(R.id.reg_login);
        mRegprogress = (ProgressBar) findViewById(R.id.reg_progress);

        mRegbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mRegemail.getText().toString();
                String pass = mRegpass.getText().toString();
                String confirm_pass = mRegconfirmpass.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) & !TextUtils.isEmpty(confirm_pass)){

                    if(pass.equals(confirm_pass)){

                        mRegprogress.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    sendToMain();


                                } else {

                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                                }

                                mRegprogress.setVisibility(View.INVISIBLE);

                            }
                        });

                    } else{

                        Toast.makeText(RegisterActivity.this, "Confirm Password and Password Field does not match!", Toast.LENGTH_LONG).show();

                    }


                }


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){

                sendToMain();



            }

    }

    private void sendToMain() {

        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
