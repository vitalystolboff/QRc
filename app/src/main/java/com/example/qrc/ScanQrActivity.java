package com.example.qrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanQrActivity extends AppCompatActivity {

    ImageView scanImg;
    TextView qrData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        scanImg = findViewById(R.id.scanImg);
        qrData = findViewById(R.id.qrData);

        Intent intent = getIntent();

        if (intent != null){
//            byte[] byteArray = intent.getByteArrayExtra("byte");
//            if (byteArray != null){
//                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String data = intent.getStringExtra("data");
                if(data != null){
//                    scanImg.setImageBitmap(image);
                    qrData.setText(data);
                }
            }
        }

    }