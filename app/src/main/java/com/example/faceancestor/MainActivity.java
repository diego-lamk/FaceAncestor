package com.example.faceancestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when button_start is touched, it sends to the choose picture screen*/
    public void sendToInstructions(View view){
        Intent intent = new Intent(this, InstructionsActivity.class);
        startActivity(intent);
    }

    public void sendToInfo(View view){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

}
