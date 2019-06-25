package com.example.faceancestor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.faceancestor.helper.ImageHelper;
import com.example.faceancestor.helper.SampleApp;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;


public class ConfirmPhotoActivity extends AppCompatActivity {


    // Background task of face detection.
    private class DetectionTask extends AsyncTask<InputStream, String, Face[]> {
        private boolean mSucceed = true;

        @Override
        protected Face[] doInBackground(InputStream... params) {
            // Get an instance of face service client to detect faces in image.
            FaceServiceClient faceServiceClient = SampleApp.getFaceServiceClient();
            try {
                publishProgress("Detecting...");

                // Start detection.
                return faceServiceClient.detect(
                        params[0],  /* Input stream of image to detect */
                        true,       /* Whether to return face ID */
                        true,       /* Whether to return face landmarks */
                        /* Which face attributes to analyze, currently we support:
                           age,gender,headPose,smile,facialHair */
                        new FaceServiceClient.FaceAttributeType[] {
                                FaceServiceClient.FaceAttributeType.Age,
                                FaceServiceClient.FaceAttributeType.Gender,
                                FaceServiceClient.FaceAttributeType.Smile,
                                FaceServiceClient.FaceAttributeType.Glasses,
                                FaceServiceClient.FaceAttributeType.FacialHair,
                                FaceServiceClient.FaceAttributeType.Emotion,
                                FaceServiceClient.FaceAttributeType.HeadPose,
                                FaceServiceClient.FaceAttributeType.Accessories,
                                FaceServiceClient.FaceAttributeType.Blur,
                                FaceServiceClient.FaceAttributeType.Exposure,
                                FaceServiceClient.FaceAttributeType.Hair,
                                FaceServiceClient.FaceAttributeType.Makeup,
                                FaceServiceClient.FaceAttributeType.Noise,
                                FaceServiceClient.FaceAttributeType.Occlusion
                        });
            } catch (Exception e) {
                mSucceed = false;
                publishProgress(e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

//        @Override
//        protected void onPreExecute() {
//            mProgressDialog.show();
//            addLog("Request: Detecting in image " + mImageUri);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... progress) {
//            mProgressDialog.setMessage(progress[0]);
//            setInfo(progress[0]);
//        }

        @Override
        protected void onPostExecute(Face[] result) {
//            if (mSucceed) {
//                addLog("Response: Success. Detected " + (result == null ? 0 : result.length)
//                        + " face(s) in " + mImageUri);
//            }

            // Show the result on screen when detection is done.
            setUiAfterDetection(result, mSucceed);
        }

    }

    // The URI of photo taken with camera
    private Uri mImageUri;

    private Bitmap mBitmap;

    private TextView text_analising;
    private ProgressBar progressBar;

    private Button buttonAnaliseImage;
    private Button buttonRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_photo);
        text_analising = (TextView)  findViewById(R.id.textView_analising);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonAnaliseImage = (Button) findViewById(R.id.button_analise);
        buttonRetry = (Button) findViewById(R.id.button_retry);

        text_analising.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        mImageUri = intent.getData();

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
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            if(finalBitmap != null)
                imageView.setImageBitmap(finalBitmap);
        }




    }


    /** When the analyse button is pressed, we go to other activity */
    public void onAnalysePushed(View view){
        text_analising.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        buttonRetry.setVisibility(View.GONE);
        buttonAnaliseImage.setVisibility(View.GONE);


        Intent analyseIntent = new Intent(this, AnalyseActivity.class);
        analyseIntent.setData(mImageUri);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                startActivity(analyseIntent);
            }
        }, 3000);   //3 seconds

//        // Put the image into an input stream for detection.
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
//
//        // Start a background task to detect faces in the image.
//        new DetectionTask().execute(inputStream);

    }



    public void onRetryPushed(View view){
        Intent choosePicture = new Intent(this, ChoosePictureActivity.class);
        startActivity(choosePicture);
    }

    public void onCancel(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    // Show the result on screen when detection is done.
    private void setUiAfterDetection(Face[] result, boolean succeed) {

        if (succeed) {
            // The information about the detection result.
            String detectionResult;
            if (result != null) {
                detectionResult = result.length + " face"
                        + (result.length != 1 ? "s" : "") + " detected";

                // Show the detected faces on original image.
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageBitmap(ImageHelper.drawFaceRectanglesOnBitmap(
                        mBitmap, result, true));


            } else {
                detectionResult = "0 face detected";
            }

            Intent analyseIntent = new Intent(this, AnalyseActivity.class);
            analyseIntent.setData(mImageUri);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // yourMethod();
                    startActivity(analyseIntent);
                }
            }, 3000);   //3 seconds
        }

    }

}
