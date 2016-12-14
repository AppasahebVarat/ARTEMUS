package com.demoapp.gls.app.artemusapp;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Santosh on 06-Oct-15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public abstract void onGetResponse(String response, String callFor);
}
