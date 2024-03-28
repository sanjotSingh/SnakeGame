package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

import java.util.Random;

public class Button implements Drawable {


    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // An image to represent the button
    private Bitmap buttonBitmap;

    // Objects for the game loop/thread
    private Thread mThread = null;

    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;


    float x = 200;
    float y = 20;

    /// Set up the apple in the constructor
    Button(Context context, Point sr, int s, int width, int height){
        // Load the image to the bitmap
        buttonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(buttonBitmap, width, height, false);
    }

    Button(Context context, Point sr, int s, float width, float height){
        // Load the image to the bitmap
        buttonBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
        // Resize the bitmap
        buttonBitmap = Bitmap.createScaledBitmap(buttonBitmap, (int)width, (int)height, false);
    }
    // Draw the apple
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(buttonBitmap, x, y, paint);

    }

}
