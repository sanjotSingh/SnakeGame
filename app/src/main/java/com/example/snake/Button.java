package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Button extends GameObject implements Drawable {


    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // An image to represent the button
    private Bitmap playButtonBitmap;
    private Bitmap pauseButtonBitmap;
    private Bitmap buttonBitmap;
    //An image to represent the Menu button
    private Bitmap menuButtonBitmap;

    float x = 200;
    float y = 20;
    float a = 2000;
    float b = 20;

    /// Set up the apple in the constructor
    Button(Context context, Point sr, int s, int width, int height){
        super();
        // Load the image to the bitmap
        pauseButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        playButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(pauseButtonBitmap, width, height, false);
    }

    Button(Context context, Point sr, int s, float width, float height){
        super();
        // Load the image to the bitmap
        pauseButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(pauseButtonBitmap, (int)width, (int)height, false);
    }
    // Draw the apple
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(buttonBitmap, x, y, paint);
    }
    public void toggleButtonBitmap(){

        if (buttonBitmap.sameAs(Bitmap.createScaledBitmap(playButtonBitmap, 200, 100, false) )){
            buttonBitmap=Bitmap.createScaledBitmap(pauseButtonBitmap, 200, 100, false);
        }
        else
            buttonBitmap=Bitmap.createScaledBitmap(playButtonBitmap, 200, 100, false);
    }


}
