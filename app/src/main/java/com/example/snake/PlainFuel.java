package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

class PlainFuel extends GameObject implements Drawable, Fuel {

    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    private Point mSpawnRange;
    private int mSize;
    Context context = null;
    // An image to represent the apple
    private Bitmap mBitmapFuel;

    /// Set up the apple in the constructor
    PlainFuel(Context context, Point sr, int s, Car mCar){
        super();

        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;
        // Hide the apple off-screen until the game starts
        location.x = -10;
        this.context=context;
        // Load the image to the bitmap
        mBitmapFuel = BitmapFactory.decodeResource(context.getResources(), R.drawable.fuel);

        // Resize the bitmap
        mBitmapFuel = Bitmap.createScaledBitmap(mBitmapFuel, mSize, mSize, false);
    }

    // This is called every time an apple is eaten
    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }



    // Draw the apple
    @Override
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapFuel,
                location.x * mSize, location.y * mSize, paint);

    }

    @Override
    public void setBitmap(Bitmap bitmap) {

        mBitmapFuel = Bitmap.createScaledBitmap(bitmap, mSize, mSize, false);
    }

    // Let CarGame know where the apple is
    // CarGame can share this with the snake
    Point getLocation(){
        return location;
    }



}