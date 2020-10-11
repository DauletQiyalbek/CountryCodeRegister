package com.example.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation move_left , move_right , slide_down;
    TextView welcome_title , welcome_desc;
    Button btn_login , btn_signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcome_title = findViewById(R.id.welcome_title);
        welcome_desc = findViewById(R.id.welcome_desc);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });




        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });


        move_right = AnimationUtils.loadAnimation(this,R.anim.move_right);
        welcome_desc.setAnimation(move_right);

        slide_down = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        btn_signUp.setAnimation(slide_down);
        btn_login.setAnimation(slide_down);

        move_left = AnimationUtils.loadAnimation(this,R.anim.move_left);
        welcome_title.setAnimation(move_left);

    }
}