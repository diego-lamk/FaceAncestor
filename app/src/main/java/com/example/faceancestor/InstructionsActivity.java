package com.example.faceancestor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    /** Called when button_start is touched, it sends to the choose picture screen*/
    public void sendToChoosePicture(View view){
        Intent intent = new Intent(this, ChoosePictureActivity.class);
        startActivity(intent);

    }
    public void sendToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
