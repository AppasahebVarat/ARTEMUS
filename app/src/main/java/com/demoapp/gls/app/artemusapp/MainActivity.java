package com.demoapp.gls.app.artemusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText scacEdit ;
    EditText userEdit;
    EditText passEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public  void loginUser(View view){
        scacEdit =(EditText)findViewById(R.id.scacid);
        userEdit =(EditText)findViewById(R.id.username);
        passEdit =(EditText)findViewById(R.id.password);
        String scacId = scacEdit.getText().toString();
        String userName =userEdit.getText().toString();
        String password = passEdit.getText().toString();


        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        intent.putExtra("scacId",scacId);
        intent.putExtra("userName",userName);
        intent.putExtra("password",password);
        startActivity(intent);
    }


    public void clearForm(View view){
        scacEdit =(EditText)findViewById(R.id.scacid);
        userEdit =(EditText)findViewById(R.id.username);
        passEdit =(EditText)findViewById(R.id.password);
        scacEdit.setText("");
        userEdit.setText("");
        passEdit.setText("");

    }

}
