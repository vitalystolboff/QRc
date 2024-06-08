package com.example.qrc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qrc.adapter.GenAdapter;
import com.example.qrc.model.GenModel;

import java.util.ArrayList;
import java.util.List;

public class GenerateFragment extends Fragment {
    Button toGenerateBtn;
    RecyclerView recyclerView;
    static GenAdapter genAdp;
    static List<GenModel> genList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate, container, false);
        toGenerateBtn = view.findViewById(R.id.toGenerateBtn);
        recyclerView = view.findViewById(R.id.recyclerView);

        Intent intent = getActivity().getIntent();
        if (intent != null){
            byte[] byteArray = intent.getByteArrayExtra("bitmap");
            if (byteArray != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String newLink = intent.getStringExtra("newLink");
                if(newLink != null && bitmap != null){
                    genList.clear();
                    genList.add(new GenModel(bitmap, newLink));
                    setRecycler(genList);
                }
            }
        }

        toGenerateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), GenerateActivity.class);
                startActivity(intent1);
            }
        });
        return view;
    }

    private void setRecycler(List<GenModel> genMod){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        genAdp = new GenAdapter(getContext(), genMod);
        recyclerView.setAdapter(genAdp);
    }

}