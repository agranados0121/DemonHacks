package com.example.demonhacks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StationViewHolder extends RecyclerView.ViewHolder {

    TextView searchText;

    public StationViewHolder(@NonNull View itemView) {
        super(itemView);
        searchText = itemView.findViewById(R.id.searchTextView);
    }
}
