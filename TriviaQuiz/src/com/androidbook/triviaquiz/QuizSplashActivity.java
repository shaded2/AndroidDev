package com.androidbook.triviaquiz;

import android.os.Bundle;

public class QuizSplashActivity extends QuizActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
    }
}