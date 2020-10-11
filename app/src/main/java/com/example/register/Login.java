package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Button  login_button , create_account_button , forget_password;
    TextInputLayout et_phone_login , et_password_login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_button = findViewById(R.id.sign_up_button_create_account);
        create_account_button = findViewById(R.id.sign_up_button_login);
        et_phone_login = findViewById(R.id.et_phone_sign_up);
        et_password_login = findViewById(R.id.et_password_login);
        forget_password = findViewById(R.id.forget_password);

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });



    }
}