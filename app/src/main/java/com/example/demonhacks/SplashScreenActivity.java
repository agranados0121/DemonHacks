package com.example.demonhacks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity
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


    // variables to bind to the views specified in splashscreenlayout.xml
    private ImageView ventra2Image, demonHacksImage;
    private TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreenlayout);

        Transition fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);

        // bind the variables to the views specified
        ventra2Image = (ImageView)findViewById(R.id.ventra2image);
        demonHacksImage = (ImageView)findViewById(R.id.demonhackslogo);
        textView = (TextView)findViewById(R.id.presentText);

        // define the animation
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        // bind the animations to the objects
        ventra2Image.startAnimation(animation);
        demonHacksImage.startAnimation(animation);
        textView.startAnimation(animation);

        // create the intent to go to the menu activity
        final Intent intent = new Intent(getApplicationContext(), MenuActivity.class);

        // create a thread that sleeps for awhile so that the splash screen can be properly recognized
        Thread timer = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(5000);

                }
                catch (InterruptedException e)
                {

                }
                // whether or not an exception is caught, this block will execute
                finally
                {
                    // save into the bundle the animations so that when the splash screen
                    // transitions to the title screen,
                    // the animation plays out smoothly
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                            getApplicationContext(),
                            android.R.anim.fade_in,
                            android.R.anim.fade_out).toBundle();

                    // start the activity
                    startActivity(intent, bundle);

                    // destroy the splash screen activity so that the user cannot go back to it
                    finish();
                }
            }
        };

        timer.start();

    }
}
