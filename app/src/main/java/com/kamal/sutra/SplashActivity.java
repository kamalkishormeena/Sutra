package com.kamal.sutra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_splash);

        Thread thread=new Thread(){
            @Override
            public void run() {
                synchronized (this){
                    try{
                        wait(1000);
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(getBaseContext(),WelcomeActivity.class));
                        finish();
                    }
                }
                super.run();
            }
        };
        thread.start();
    }
}
