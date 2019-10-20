package com.example.demonhacks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.widget.*;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String ACTIVITYTAG = "MainActivity";

    private TextView textView;
    private Toolbar appBar;
    private DrawerLayout menuDrawer;

    private ReportFragment reptFragment = new ReportFragment();

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

        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();

        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.launch_tracker:
                                // fragment for tracker
                                Log.i(ACTIVITYTAG, "launch tracker pressed");

                                //code here

                                menuDrawer.closeDrawers();
                                return true;
                            case R.id.launch_timetable:
                                // fragment for timetable
                                Log.i(ACTIVITYTAG, "time table pressed");

                                //code here

                                menuDrawer.closeDrawers();
                                return true;
                            case R.id.launch_twitter:
                                // fragment for cta twitter
                                Log.i(ACTIVITYTAG, "twitter pressed");

                                //code here

                                menuDrawer.closeDrawers();
                                return true;
                            case R.id.launch_reportemergency:
                                // fragment for reporting emergency
                                Log.i(ACTIVITYTAG, "report emergency pressed");

                                //code here
                                if(!reptFragment.isAdded()) {
                                    ft.replace(R.id.flContent, reptFragment);
                                    // add the transaction to the backstack
                                    ft.addToBackStack(null);
                                    // commit the transaction
                                    ft.commit();
                                    // force android to execute the committed transaction
                                    //ft.executePendingTransactions();
                                }

                                menuDrawer.closeDrawers();
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
                Log.i(ACTIVITYTAG, "help pressed");
                return true;
            case R.id.reportIssue:
                //report issue fragment
                Log.i(ACTIVITYTAG, "report Issue pressed");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
