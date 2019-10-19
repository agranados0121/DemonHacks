package com.example.demonhacks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

public class ReportActivity extends AppCompatActivity
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

    Button reportButton, reportButton2;
    ImageView cameraImage;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.reportlayout);

        // bind the objects to names
        reportButton = (Button)findViewById(R.id.reportButton);
        reportButton2 = (Button)findViewById(R.id.reportButton2);
        cameraImage = (ImageView)findViewById(R.id.cameraImage);

        // set the alphas for aesthetic looks
        cameraImage.setAlpha(.666f);
        reportButton2.setAlpha(.333f);

        // set a listener to allow the user to take a photo to report an incident
        reportButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, 0);
            }
        });
    }



    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data)
    {
        // IFF the user actually takes a photo
        if (resCode != 0)
        {
            super.onActivityResult(reqCode, resCode, data);

            // get the image as a bitmap
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            // revert alpha back to normal and assign the bitmap to the imageview
            cameraImage.setAlpha(1.0f);
            cameraImage.setImageBitmap(bitmap);

            // rewrite the first button
            reportButton.setText("Redo");

            // revert alpha and allow the button to be clickable
            reportButton2.setAlpha(1.0f);
            reportButton2.setEnabled(true);

            // reassign the functionality of the 1st button
            reportButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //reportButton.setVisibility(View.GONE);
                    Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 0);
                }
            });

            // take the image that the user has taken and passes it along to the next activity
            // so that the user can fill out the rest of the report
            reportButton2.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(getApplicationContext(), SubmitReportActivity.class);

                    // save into the bundle the animations so that when the splash screen
                    // transitions to the title screen,
                    // the animation plays out smoothly
                    Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                            getApplicationContext(),
                            android.R.anim.fade_in,
                            android.R.anim.fade_out).toBundle();

                    // start the activity
                    startActivity(intent, bundle);
                }
            });
        }

    }
}
