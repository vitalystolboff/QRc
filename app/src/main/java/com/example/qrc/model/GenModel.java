package com.example.qrc.model;

import android.graphics.Bitmap;

public class GenModel {
    Bitmap qrImgId;
    String qrLink;

    public GenModel(Bitmap qrImgId, String qrLink) {
        this.qrImgId = qrImgId;
        this.qrLink = qrLink;
    }

    public Bitmap getQrImgId() {
        return qrImgId;
    }

    public void setQrImgId(Bitmap qrImgId) {
        this.qrImgId = qrImgId;
    }

    public String getQrLink() {
        return qrLink;
    }

    public void setQrLink(String qrLink) {
        this.qrLink = qrLink;
    }
}
