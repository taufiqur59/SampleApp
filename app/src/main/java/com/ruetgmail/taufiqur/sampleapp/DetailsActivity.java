package com.ruetgmail.taufiqur.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private static final String EXTRA_POSITION = "com.example.nishat.abc.position";
    private static final String EXTRA_NAME = "com.example.nishat.abc.name";
    private static final String EXTRA_DETILS = "com.example.nishat.abc.details";
    private int mPosition;
    private String mName, mDetails;
    private ImageView mTheImageView;
    private TextView mTheTextView, mDetailsTextView;
    public static Intent newIntent(Context packageContext, int position, String name, String details) {
        Intent intent = new Intent(packageContext, DetailsActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_DETILS, details);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mPosition = (int) getIntent().getSerializableExtra(EXTRA_POSITION);
        mName = (String) getIntent().getSerializableExtra(EXTRA_NAME);
        mDetails = (String) getIntent().getSerializableExtra(EXTRA_DETILS);

        mTheImageView =(ImageView) findViewById(R.id.person_details_image);
        mTheImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(),
                getResources().getIdentifier(String.format(Locale.US,"s%02d", mPosition), "drawable", getPackageName()),
                50,50));

        mTheTextView = (TextView) findViewById(R.id.the_name);
        mTheTextView.setText(mName);

        mDetailsTextView = (TextView) findViewById(R.id.details_text);
        mDetailsTextView.setText(mDetails);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}