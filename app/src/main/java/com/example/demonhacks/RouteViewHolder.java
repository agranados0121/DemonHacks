package com.example.demonhacks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RouteViewHolder extends RecyclerView.ViewHolder {

    TextView stationText;
    TextView directionText;
    TextView arrivalsText;
    TextView timeText;

    public RouteViewHolder(@NonNull View itemView) {
        super(itemView);
        stationText = itemView.findViewById(R.id.stationTextView);
        directionText = itemView.findViewById(R.id.directionTextView);
        arrivalsText = itemView.findViewById(R.id.arrivalsTextView);
        timeText = itemView.findViewById(R.id.timeTextView);
    }
}
