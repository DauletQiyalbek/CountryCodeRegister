package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;



public class VerifyOTP extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    String mAuthVerificationId;
    Button verify_button;
    PinView et_verify;
    TextView txt_sob;
    ProgressBar progressBar2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_o_t_p);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("AuthCredentials");



        verify_button = findViewById(R.id.verify_button);
        et_verify = findViewById(R.id.et_verify);
        txt_sob = findViewById(R.id.txt_sob);
        progressBar2 = findViewById(R.id.progressBar2);

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_verify.getText().toString().isEmpty()){
                    txt_sob.setVisibility(View.VISIBLE);
                    txt_sob.setText("Please fill in the form and try again.");
                }
                else {
                    progressBar2.setVisibility(View.VISIBLE);
                    verify_button.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId,et_verify.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                txt_sob.setVisibility(View.VISIBLE);
                                txt_sob.setText("The was an error verifying OTP");

                            }
                        }
                        progressBar2.setVisibility(View.INVISIBLE);
                        verify_button.setVisibility(View.VISIBLE);
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null){
            sendUserToHome();
        }
    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(VerifyOTP.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }


}