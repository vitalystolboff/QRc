# Генератор и сканер QR кодов в Android Studio

Проект реализован на базе языка Java с использованием библиотек [QRGenerator](https://github.com/androidmads/QRGenerator) и [Code Scanner](https://github.com/yuriy-budiyev/code-scanner).

Приложение состоит из трех фрагментов:
1. Генератор QR кодов
2. Сканер QR кодов
3. История отсканированных кодов

### Генератор QR кодов

Первый фрагмент включает себя список добавленных QR кодов в виде RecyclerView и кнопку для перехода к форме добавления QR кода


![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/0c581345-1229-4902-9e85-5e3dfe137f7c)

Код данного фрагмента:
```
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
```
Форма добавления нового кода выглядит следующим образом:

![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/5940eda1-0cc5-4cd5-95bb-0911e1b5cd31)

Код: 
```
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
```
После нажатия на кнопку "Сгенерировать" программа считывает введеный пользователем текст, и с помощью класса QRGEncoder генерирует QR и отправляет его на предыдущий фрагмент, где он добавляется в список и отображается в RecyclerView

Пример работы: 

![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/93df351c-fcab-45b4-9fe5-be65f7d8d42e)    ![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/08e10b74-c544-4d73-bac0-c5101ffb645b)

### Сканер QR кодов и история

Фрагмент полностью заполнен элементом CodeScannerView из библиотеки CodeScanner

![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/71a590cc-6bc9-44e1-baed-09f3fc4341e9)

Логика довольна проста. После сканирования QR кода полученные данные с помощью intent отправляются в HistoryFragment, где заносятся в список и отображаются в таком же виде, как и в GenerateFragment:

```
package com.example.qrc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScannerFragment extends Fragment {
    private CodeScanner mCodeScanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Boolean fromScaning = true;
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.putExtra("data", result.getText().toString());
//                        intent.putExtra("byte", result.getRawBytes());
//                        intent.putExtra("fromScanner", fromScaning);
                        startActivity(intent);
                        Toast.makeText(activity, result.getRawBytes().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}
```

Код в HistoryFragment:
```
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
            String scacingStr = intent.getStringExtra("data");
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
```

Пример работы

![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/923357dc-c7ad-496c-a0d8-ae8ae2003085)   ![изображение](https://github.com/vitalystolboff/QRc/assets/152699681/f25655df-dece-4965-b747-6e5199ad6350)
