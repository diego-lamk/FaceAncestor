package com.example.faceancestor;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }
    public void sendToMainActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}
