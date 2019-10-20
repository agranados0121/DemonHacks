package com.example.demonhacks;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SubmitReportActivity extends AppCompatActivity
{
    private ReportFragment reportFragment = new ReportFragment();
    private FragmentManager fragmentManager;

    private FrameLayout frameLayout;


    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.submitreportlayout);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frameLayout, new ReportFragment());

        transaction.commit();

        fragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        setLayout();
                    }
                }
        );
    }

    private void setLayout()
    {
        // determine whether or not the image fragment has been added
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        }
    }
}
