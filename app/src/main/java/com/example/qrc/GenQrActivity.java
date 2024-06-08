package com.example.qrc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GenQrActivity extends AppCompatActivity {

    ImageView genPageImg;
    TextView genPageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating);

        genPageImg = findViewById(R.id.genPageImg);
        genPageText = findViewById(R.id.genPageText);

        Intent intent = getIntent();
        if (intent != null){
            byte[] byteArray = intent.getByteArrayExtra("Image");
            if (byteArray != null){
                Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                String name = intent.getStringExtra("Name");
                if(name != null && image != null){
                    genPageImg.setImageBitmap(image);
                    genPageText.setText(name);
                }
            }
        }


    }
}