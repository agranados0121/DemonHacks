package com.example.demonhacks;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity
{
    // shows the system bars by removing all the flags
    // except for the ones that make the content appear
    // under the system bars
    private void showSystemUI()
    {
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    private void hideSystemUI()
    {
        View decorView = getWindow().getDecorView();

        // enables regular immersive mode
        // for "lean back" mode, remove flag_immersive
        // or for sticky, replace with immersive_sticky
        decorView.setSystemUiVisibility(
                // set the content to appear under the system bars so
                // that the content does not resize when the system
                // bars hide and show
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // hide the nav and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        WindowManager.LayoutParams attr = getWindow().getAttributes();
        attr.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setAttributes(attr);
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus)
            hideSystemUI();
    }

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.menulayout);
    }
}
