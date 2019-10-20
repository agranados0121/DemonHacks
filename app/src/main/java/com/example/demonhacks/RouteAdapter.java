package com.example.demonhacks;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteViewHolder> {

    private static final String TAG = "RouteAdapter";
    private ArrayList<Route> routeList;
    private MainActivity mainActivity;

    RouteAdapter(ArrayList<Route> routeList, MainActivity mainActivity) {
        this.routeList = routeList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: CREATING ROW");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: POPULATING DATA");

        Route selection = routeList.get(position);

        String color = selection.getHexColor();
        holder.trainImage.setColorFilter(Color.parseColor(color));

        Log.d(TAG, "onBindViewHolder: COLOR" + color);

        holder.stationText.setText(selection.getStationName());
        holder.directionText.setText(selection.getDestination());

        int size = selection.getTrains().size();
        for (int i = 0; i < size; i++) {
            if (i == (size - 1)){
                holder.arrivalsText.append(selection.getTrains().get(i).getArrivalTime());
                holder.timeText.append(selection.getTrains().get(i).getTimeRemaining());
            } else {
                holder.arrivalsText.append(selection.getTrains().get(i).getArrivalTime() + "\n");
                holder.timeText.append(selection.getTrains().get(i).getTimeRemaining() + "\n");
            }
        }
        holder.directionText.setTextColor(Color.parseColor(color));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + routeList.size());
        return routeList.size();
    }
}
