package com.example.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    Button sign_up_button_create_account ;
    CountryCodePicker countryCodePicker;
    TextInputLayout et_phone_sign_up;
    ProgressBar progressBar;
    TextView txt_sob_sign_up;
    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        txt_sob_sign_up = findViewById(R.id.txt_sob_sign_up);
        progressBar = findViewById(R.id.progressBar);
        et_phone_sign_up = findViewById(R.id.et_phone_sign_up);
        countryCodePicker = findViewById(R.id.country_code_picker_sign_up);
        sign_up_button_create_account = findViewById(R.id.sign_up_button_create_account);
        sign_up_button_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = "+" + countryCodePicker.getSelectedCountryCode() + et_phone_sign_up.getEditText().getText().toString();
                if(!et_phone_sign_up.getEditText().getText().toString().isEmpty() && et_phone_sign_up.getEditText().getText().toString().length() == 10){

                    sign_up_button_create_account.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            phoneNum,
                            60,
                            TimeUnit.SECONDS,
                            SignUp.this,
                            mCallbacks
                    );

                }
                else {
                    txt_sob_sign_up.setText("Please fill in the form to continue.");
                    txt_sob_sign_up.setVisibility(View.VISIBLE);
                }
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                txt_sob_sign_up.setText("Verification Failed, please try again.");
                txt_sob_sign_up.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                sign_up_button_create_account.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCodeSent(final String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                Intent otpIntent = new Intent(SignUp.this, VerifyOTP.class);
                                otpIntent.putExtra("AuthCredentials", s);
                                startActivity(otpIntent);
                                finish();
                            }
                        },
                        10000);
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null){
            sendUserToHome();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendUserToHome();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                txt_sob_sign_up.setVisibility(View.VISIBLE);
                                txt_sob_sign_up.setText("There was an error verifying OTP");
                            }
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        sign_up_button_create_account.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(SignUp.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}