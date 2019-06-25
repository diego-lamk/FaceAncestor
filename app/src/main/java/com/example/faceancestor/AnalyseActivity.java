package com.example.faceancestor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.faceancestor.helper.ImageHelper;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class AnalyseActivity extends AppCompatActivity {

    private Uri mImageUri;

    private Bitmap mBitmap;


    private ImageView imageMap;
    private ImageView imageChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse);

        imageMap = (ImageView) findViewById(R.id.imageMap);
        imageChart = (ImageView) findViewById(R.id.imageChart);

        imageMap.setVisibility(View.GONE);

        CircleImageView circleImageView = findViewById(R.id.profile_image);

        Intent intent = getIntent();
        mImageUri = intent.getData();

        Random rand = new Random();

        int aux = rand.nextInt(5);

        switch (aux){
            case 0:
                imageMap.setImageDrawable(getResources().getDrawable(R.drawable.black_map, getApplicationContext().getTheme()));
                imageChart.setImageDrawable(getResources().getDrawable(R.drawable.black, getApplicationContext().getTheme()));
                break;
            case 1:
                imageMap.setImageDrawable(getResources().getDrawable(R.drawable.chino_baltico_map, getApplicationContext().getTheme()));
                imageChart.setImageDrawable(getResources().getDrawable(R.drawable.chino_baltico, getApplicationContext().getTheme()));
                break;
            case 2:
                imageMap.setImageDrawable(getResources().getDrawable(R.drawable.indian_map, getApplicationContext().getTheme()));
                imageChart.setImageDrawable(getResources().getDrawable(R.drawable.indian, getApplicationContext().getTheme()));
                break;
            case 3:
                imageMap.setImageDrawable(getResources().getDrawable(R.drawable.sp_it_map, getApplicationContext().getTheme()));
                imageChart.setImageDrawable(getResources().getDrawable(R.drawable.sp_it, getApplicationContext().getTheme()));
                break;
            case 4:
                imageMap.setImageDrawable(getResources().getDrawable(R.drawable.spanish_map, getApplicationContext().getTheme()));
                imageChart.setImageDrawable(getResources().getDrawable(R.drawable.spanish, getApplicationContext().getTheme()));
                break;
        }

        mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                mImageUri, getContentResolver());
        Bitmap finalBitmap;

        if (mBitmap != null) {
            //Make the bitmap square
            if (mBitmap.getWidth() >= mBitmap.getHeight()){

                finalBitmap = Bitmap.createBitmap(
                        mBitmap,
                        mBitmap.getWidth()/2 - mBitmap.getHeight()/2,
                        0,
                        mBitmap.getHeight(),
                        mBitmap.getHeight()
                );

            }else{

                finalBitmap = Bitmap.createBitmap(
                        mBitmap,
                        0,
                        mBitmap.getHeight()/2 - mBitmap.getWidth()/2,
                        mBitmap.getWidth(),
                        mBitmap.getWidth()
                );
            }

            // Show the image on screen.
            if(finalBitmap != null)
                circleImageView.setImageBitmap(finalBitmap);
        }

    }

    public void onDone(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    public void toogleMapChart(View view){
        if(imageMap.getVisibility() != View.GONE){
            imageMap.setVisibility(View.GONE);
            imageChart.setVisibility(View.VISIBLE);
        }else{
            imageMap.setVisibility(View.VISIBLE);
            imageChart.setVisibility(View.GONE);
        }
    }


}
