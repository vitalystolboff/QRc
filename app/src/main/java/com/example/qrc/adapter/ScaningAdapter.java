package com.example.qrc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrc.R;
import com.example.qrc.model.Scaning;

import java.util.List;

public class ScaningAdapter extends RecyclerView.Adapter<ScaningAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Scaning> scanings;

    public ScaningAdapter(Context context, List<Scaning> scanings) {
        this.inflater = LayoutInflater.from(context);
        this.scanings = scanings;
    }

    @NonNull
    @Override
    public ScaningAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.scaning_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScaningAdapter.ViewHolder holder, int position) {
        Scaning scaning = scanings.get(position);
        holder.scanningLink.setText(scaning.getLink());

    }

    @Override
    public int getItemCount() {
        return scanings.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView scanningLink;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scanningLink = itemView.findViewById(R.id.scaningLink);
        }
    }
}
