package com.example.demonhacks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ReportFragment extends Fragment
{
    Button reportButton, reportButton2;
    ImageView cameraImage;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        // retain this fragment across activity reconfigs
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.reportlayout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

        View view = getView();

        reportButton = (Button) view.findViewById(R.id.reportButton);
        reportButton2 = (Button) view.findViewById(R.id.reportButton2);

        cameraImage = (ImageView) view.findViewById(R.id.cameraImage);

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
    public void onActivityResult(int reqCode, int resCode, Intent data)
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
                    //Intent intent = new Intent(view.getContext(), SubmitReportActivity.class);

                    //
                    //intent.putExtra("reportImage", cameraImage.getId());
                    // save into the bundle the animations so that when the splash screen
                    // transitions to the title screen,
                    // the animation plays out smoothly
                    //Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(
                    //        view.getContext(),
                    //        android.R.anim.fade_in,
                    //        android.R.anim.fade_out).toBundle();

                    // start the activity
                    //startActivity(intent, bundle);
//                    Fragment fragment = new ReportFragment();
//
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                    transaction.replace(R.id.frameLayout, fragment);
//                    transaction.addToBackStack(null);
//
//                    transaction.commit();
                }
            });
        }
    }
}
