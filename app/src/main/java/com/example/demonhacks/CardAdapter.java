package com.example.demonhacks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.demonhacks.R;

public class CardAdapter extends ArrayAdapter<String>
{
    int[] cardImages;
    String[] cardNames;
    String[] cardNumbers;
    String[] fareAmounts;

    Context context;

    // constructor to hold the custom layout that will hold the images
    // and its texts
    public CardAdapter(Context context, int[] cardImages, String[] cardNames, String[] cardNumbers, String[] fareAmounts)
    {
        // call the super constructor to make the custom layout
        super(context, R.layout.cardlayout);

        // initialize the things
        this.context = context;
        this.cardImages = cardImages;
        this.cardNames = cardNames;
        this.cardNumbers = cardNumbers;
        this.fareAmounts = fareAmounts;
    }


    // get the clickable area/length(?)
    // if this is forgotten, the adapter will not know the size of the list
    @Override
    public int getCount()
    {
        return cardNames.length;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // initialize the view holder
        ViewHolder viewHolder = new ViewHolder();

        // check if there is no instances of the convert view.
        // if not, inflate and set the view holders
        if (convertView == null)
        {
            // inflater
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // convert view
            convertView = inflater.inflate(R.layout.cardlayout, parent, false);

            // set the view holder
            viewHolder.cardImage = (ImageView)convertView.findViewById(R.id.ventraCardImage);
            viewHolder.cardName = (TextView)convertView.findViewById(R.id.cardName);
            viewHolder.cardNumber = (TextView)convertView.findViewById(R.id.cardNumber);
            viewHolder.fareAmount = (TextView)convertView.findViewById(R.id.fareAmount);

            // set the convert view's tags to the view holder
            convertView.setTag(viewHolder);
        }
        // else, that means that there is a convert view so all that is needed to do is to
        // convert it to the view holder
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // set the custom layout's elements
        viewHolder.cardImage.setImageResource(cardImages[position]);
        viewHolder.cardName.setText(cardNames[position]);
        viewHolder.cardNumber.setText(cardNumbers[position]);
        viewHolder.fareAmount.setText(fareAmounts[position]);

        // return view once things are done
        return convertView;
    }


    // create a view holder so that the OS will not constantly make calls to findViewById
    // which is the source for lag and high RAM consumption
    static class ViewHolder
    {
        ImageView cardImage;
        TextView cardName;
        TextView cardNumber;
        TextView fareAmount;
    }
}