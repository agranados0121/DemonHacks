package com.example.demonhacks;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class CardFragment extends Fragment
{

    // initializing the views
    ListView listView;

    ImageView cardImage;
    TextView cardName;
    TextView cardNumber;
    TextView fareAmount;

    int[] cardImages = {R.drawable.ventra_pattern,
                        R.drawable.ventra_pattern,
                        R.drawable.ventra_pattern,
                        R.drawable.ventra_pattern,
                        R.drawable.ventra_pattern};
    String[] cardNames = {"Some", "Body", "Once", "Told", "Me"};
    String[] cardNumbers = {"1933", "0391", "9453", "0931", "5256"};
    String[] fareAmounts = {"$0.00", "$13.37", "$4.20", "-$1000.00", "$1020.19"};



    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        setRetainInstance(true);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.cardlist_fragment, container, false);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle bundle)
    {
        super.onActivityCreated(bundle);

        View view = getView();

        listView = (ListView)view.findViewById(R.id.listView);

        cardName = (TextView)view.findViewById(R.id.cardName);
        cardNumber = (TextView)view.findViewById(R.id.cardNumber);
        fareAmount = (TextView)view.findViewById(R.id.fareAmount);

        cardImage = (ImageView)view.findViewById(R.id.ventraCardImage);

        // initialize the programmer's custom adapter
        CardAdapter cardAdapter = new CardAdapter(view.getContext(), cardImages, cardNames, cardNumbers, fareAmounts);
        listView.setAdapter(cardAdapter);
    }



    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data)
    {

    }
}
