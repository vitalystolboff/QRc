package com.example.qrc;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class GenerateActivity extends AppCompatActivity {

    Button generateBtn;
    EditText inputText;
    ImageView QRimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        generateBtn = findViewById(R.id.generateBtn);
        inputText = findViewById(R.id.inputText);
        QRimage = findViewById(R.id.QRimg);

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTextStr = inputText.getText().toString();
                QRGEncoder qrgEncoder = new QRGEncoder(inputTextStr, null, QRGContents.Type.TEXT, 500);
                qrgEncoder.setColorBlack(Color.WHITE);
                qrgEncoder.setColorWhite(Color.BLACK);
                try {
                    if (!inputTextStr.isEmpty()){
                        Bitmap bitmap = qrgEncoder.getBitmap();
                        QRimage.setImageBitmap(bitmap);


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();


                        Intent intent = new Intent(GenerateActivity.this, MainActivity.class);
                        Boolean fromGenerate = true;
                        intent.putExtra("fromGenerate", fromGenerate);
                        intent.putExtra("bitmap", byteArray);
                        intent.putExtra("newLink", inputTextStr);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Введите текст!", Toast.LENGTH_LONG).show();
                        inputText.setHintTextColor(Color.RED);
                    }
                } catch(Exception e){
                    Log.v(TAG, e.toString());
                }
            }
        });

    }
}