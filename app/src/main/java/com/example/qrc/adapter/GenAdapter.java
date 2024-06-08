package com.example.qrc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrc.GenQrActivity;
import com.example.qrc.R;
import com.example.qrc.model.GenModel;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class GenAdapter extends RecyclerView.Adapter<GenAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<GenModel> qrCodes;

    public GenAdapter(Context context, List<GenModel> qrCodes) {
        this.inflater = LayoutInflater.from(context);
        this.qrCodes = qrCodes;
    }

    @NonNull
    @Override
    public GenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.generating_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenAdapter.ViewHolder holder, int position) {
        GenModel genQr = qrCodes.get(position);
        holder.gqrcImg.setImageBitmap(genQr.getQrImgId());
        holder.gqrcTxt.setText(genQr.getQrLink());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                qrCodes.get(position).getQrImgId().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent intent = new Intent(v.getContext(), GenQrActivity.class);
                intent.putExtra("Image", byteArray);
                intent.putExtra("Name", qrCodes.get(position).getQrLink());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return qrCodes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView gqrcImg;
        final TextView gqrcTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gqrcImg = itemView.findViewById(R.id.gqrcImg);
            gqrcTxt = itemView.findViewById(R.id.gqrcTxt);
        }
    }
}
