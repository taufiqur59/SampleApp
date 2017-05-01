package com.ruetgmail.taufiqur.sampleapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AboutAppActivity extends AppCompatActivity {
    private Button mYouTubeButton, mTwitterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mYouTubeButton = (Button) findViewById(R.id.dev_at_youtube);
        mYouTubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dev_youtube_adrs_browser)));
                PackageManager packageManager = getPackageManager();
                if (packageManager.resolveActivity(i,
                        PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    startActivity(i);
                }
            }
        });
        mTwitterButton = (Button) findViewById(R.id.dev_at_twitter);
        mTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dev_twitter_adrs)));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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