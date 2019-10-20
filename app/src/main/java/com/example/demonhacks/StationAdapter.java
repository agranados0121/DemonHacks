package com.example.demonhacks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationViewHolder> {

    private ArrayList<Station> stationList;
    private SearchActivity searchActivity;

    StationAdapter(ArrayList<Station> stationList, SearchActivity searchActivity) {
        this.stationList = stationList;
        this.searchActivity = searchActivity;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_item, parent, false);
        itemView.setOnClickListener(searchActivity);
        itemView.setOnLongClickListener(searchActivity);
        return new StationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Station selection = stationList.get(position);
        holder.searchText.setText(selection.getName());
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }
}
