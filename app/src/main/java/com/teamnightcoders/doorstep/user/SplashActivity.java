package com.teamnightcoders.doorstep.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SystemClock.sleep(2000);
        Intent intent=new Intent(this,WalkThroughActivity.class);
        startActivity(intent);
        finish();
    }
}