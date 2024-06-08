package com.example.qrc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.qrc.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        ReplaceFragment(new GenerateFragment());

        Intent intent = getIntent();
        if(intent != null){
            Boolean fromScanner = intent.getBooleanExtra("fromScanner", false);
            Boolean fromGenerate = intent.getBooleanExtra("fromGenerate", false);
            if(fromScanner == true){
                ReplaceFragment(new HistoryFragment());
            } else if (fromGenerate == true) {
                ReplaceFragment(new GenerateFragment());
            }
        }

        activityMainBinding.bottomNavigationView.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.generate){
                ReplaceFragment(new GenerateFragment());
            } else if (item.getItemId() == R.id.scanner) {
                ReplaceFragment(new ScannerFragment());
            }else if (item.getItemId() == R.id.history) {
                ReplaceFragment(new HistoryFragment());
            }
            return true;
        });
    }

    public void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}