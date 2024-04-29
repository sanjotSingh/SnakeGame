package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;


public interface Fuel {

    public void spawn(); //spawn fuel on screen
    public void draw(Canvas canvas, Paint paint); //Draw duel
    void setBitmap(Bitmap bitmap); // Set the bitmap for the fuel
}
