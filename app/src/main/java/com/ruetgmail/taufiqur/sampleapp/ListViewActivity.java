package com.ruetgmail.taufiqur.sampleapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private HashMap<String, String> dictionary;
    private ArrayList<String> mNames, mDetails;
    private ListView lview;
    private ListViewAdapter lviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                Intent intent = null;
                switch (menuItem.getItemId()) {
                    case R.id.nav_rate:
                        try {
                            appLinks(R.string.app_link);
                        } catch (ActivityNotFoundException e) {
                            appLinks(R.string.alternate_app_link);
                        }
                        break;
                    case R.id.nav_share:
                        intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.awesome_app));
                        intent.putExtra(Intent.EXTRA_SUBJECT,
                                getString(R.string.app_sharing_subject));
                        intent = Intent.createChooser(intent, getString(R.string.share_app));
                        startActivity(intent);
                        break;
                    case R.id.nav_more_free_apps:
                        try {
                            appLinks(R.string.developer_page);
                        } catch (ActivityNotFoundException e) {
                            appLinks(R.string.alternate_dev_page);
                        }
                        break;
                    case R.id.nav_about_this_app:
                        intent = new Intent(ListViewActivity.this, AboutAppActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_quit:
                        finish();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


        dictionary = new HashMap<>();
        readAllDefinitions();
        ArrayList<String> chosenWord = new ArrayList<>(dictionary.keySet());
        Collections.sort(chosenWord);

        mNames = new ArrayList<>();
        mDetails = new ArrayList<>();

        for (int i = 0; i < chosenWord.size(); i++) {
            String word = chosenWord.get(i);
            //System.out.println(chosenWord.get(i));
            String correctDefinition = dictionary.get(word);
            String[] pieces = correctDefinition.split("  ");
            if (pieces.length >= 2) {
                mNames.add(pieces[0]);
                mDetails.add(pieces[1]);
            }
        }

        lview = (ListView) findViewById(R.id.definitionList);
        lviewAdapter = new ListViewAdapter(this, mNames);
        lview.setAdapter(lviewAdapter);
        lview.setOnItemClickListener(this);
    }

    private void appLinks(int id) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getResources().getString(id)));
        startActivity(intent);
    }

    private void readAllDefinitions() {
        //read the pre-existing starting words from gre_word.txt
        Scanner scan = null;
        scan = new Scanner(getResources().openRawResource(R.raw.item_list));
        readWordsFromFIle(scan);
    }

    private void readWordsFromFIle(Scanner scan) {
        while (scan.hasNext()) {
            String line = scan.nextLine();
            //System.out.println(line);
            String[] pieces = line.split("   ");
            //System.out.println(pieces[0] + " -- "+ pieces[1]);
            if (pieces.length >= 2) {
                dictionary.put(pieces[1], pieces[2]);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String name = mNames.get(position);
        String details = mDetails.get(position);
        Intent i= DetailsActivity.newIntent(getApplicationContext(), position, name, details);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }

}

