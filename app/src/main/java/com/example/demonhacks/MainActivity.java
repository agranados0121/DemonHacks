package com.example.demonhacks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.*;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Toolbar appBar;
    private DrawerLayout menuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(appBar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.ic_action_name);

        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.launch_tracker:
                                // fragment for tracker

                                return true;
                            case R.id.launch_timetable:
                                // fragment for timetable

                                return true;
                            case R.id.launch_twitter:
                                // fragment for cta twitter

                                return true;
                            case R.id.launch_reportemergency:
                                // fragment for reporting emergency

                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuDrawer.openDrawer(GravityCompat.START);
                return true;
            case R.id.help:
                // help fragment
                return true;
            case R.id.reportIssue:
                //report issue fragment
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
