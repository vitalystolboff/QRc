package com.example.qrc;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qrc.adapter.GenAdapter;
import com.example.qrc.adapter.ScaningAdapter;
import com.example.qrc.model.GenModel;
import com.example.qrc.model.Scaning;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    static List<Scaning> scanings = new ArrayList<>();
    static ScaningAdapter scaningAdapter;
    RecyclerView recyclerScanning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerScanning = view.findViewById(R.id.recyclerHistory);
        Intent intent = getActivity().getIntent();
        if (intent != null){
            String scacingStr = intent.getStringExtra("scanning");
            if(scacingStr != null){
                scanings.clear();
                scanings.add(new Scaning(scacingStr));
                setRecyclerHistory(scanings);
            }
        }

        return view;
    }
    private void setRecyclerHistory(List<Scaning> list){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerScanning.setLayoutManager(layoutManager);
        scaningAdapter = new ScaningAdapter(getContext(), list);
        recyclerScanning.setAdapter(scaningAdapter);
    }
}