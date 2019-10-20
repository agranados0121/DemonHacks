package com.example.demonhacks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class SubmitReportFragment extends Fragment
{
    ImageView imageView;
    Spinner spinner;
    EditText editText;
    Button submitButton;

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
        View view = inflater.inflate(R.layout.submitreportlayout, container, false);

        return view;
    }



    @Override
    public void onActivityCreated (Bundle bundle)
    {
        super.onActivityCreated(bundle);

        View view = getView();

        imageView = (ImageView)view.findViewById(R.id.imageEvidence);

        spinner = (Spinner)view.findViewById(R.id.subjectSelection);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.reportTypes, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        editText = (EditText)view.findViewById(R.id.editText);

        submitButton = (Button)view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().matches(""))
                {
                    Toast.makeText(v.getContext(), "Please add a comment", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(v.getContext(), "Thanks for reporting the incident", Toast.LENGTH_LONG).show();

                    getActivity().getSupportFragmentManager().beginTransaction().remove(SubmitReportFragment.this).commit();
                }
            }
        });

    }
}
