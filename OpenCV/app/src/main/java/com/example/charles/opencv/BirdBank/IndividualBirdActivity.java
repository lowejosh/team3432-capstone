package com.example.charles.opencv.BirdBank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.charles.opencv.R;
import com.example.charles.opencv.Tables.Bird;

/**
 * Displays detailed information about the bird on the app screen.
 */
public class IndividualBirdActivity extends AppCompatActivity {
    TextView mBirdName;
    TextView mBirdSeen;
    TextView mBirdMinSize;
    TextView mBirdMaxSize;
    ImageView mBirdImage;
    Bird bird;
    String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.individual_bird);

        mBirdName = (TextView) findViewById(R.id.textView6);
        mBirdSeen = (TextView) findViewById(R.id.textView5);
        mBirdMinSize = (TextView) findViewById(R.id.textView4);
        mBirdMaxSize = (TextView) findViewById(R.id.textView3);
        mBirdImage = (ImageView) findViewById(R.id.bird_image);

        updateBirdPage();
    }

    /**
     * Updates the bird page with stored information about the bird
     */
    private void updateBirdPage() {
        bird = ListBirdAdapter.mBirdClicked;
        date = ListBirdAdapter.mDateOfBirdClicked;

        mBirdName.setText(bird.getName());
        mBirdSeen.setText("Identified on " + date + "\n");
        mBirdMinSize.setText("Min size: " + bird.getMinSize());
        mBirdMaxSize.setText("Max Size: " + bird.getMaxSize());
        mBirdImage.setImageBitmap(bird.getBirdImage(this));
    }
}