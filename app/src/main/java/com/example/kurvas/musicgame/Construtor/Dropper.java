package com.example.kurvas.musicgame.Construtor;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

public class Dropper {
    ImageView view;
    int flag;
    public Dropper(ImageView view, int flag){
        this.view=view;
        this.flag=flag;
    }

    public ImageView getCurrentView() {
        return view;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCurrentView(ImageView view) {
        this.view = view;
    }
}
