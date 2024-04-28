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
        buttonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        menuButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu10);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(buttonBitmap, width, height, false);
        menuButtonBitmap = Bitmap.createScaledBitmap(menuButtonBitmap, width, height, false);
    }

    Button(Context context, Point sr, int s, float width, float height){
        super();
        // Load the image to the bitmap
        buttonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        menuButtonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu10);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(buttonBitmap, (int)width, (int)height, false);
        menuButtonBitmap = Bitmap.createScaledBitmap(menuButtonBitmap, (int)width, (int)height, false);
    }
    // Draw the apple
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(buttonBitmap, x, y, paint);
        canvas.drawBitmap(menuButtonBitmap, a, b, paint);
    }


}
