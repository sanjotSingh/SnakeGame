package com.example.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class FastFuel extends FuelDecorator{


    // The location of the apple on the grid
    // Not in pixels
    private Point location = new Point();

    // The range of values we can choose from
    // to spawn an apple
    private Point mSpawnRange;
    private int mSize;
    Context context;
    // An image to represent the apple
    private Bitmap mBitmapFuel;;
    public FastFuel(Fuel newFuel,Context context, int mSize,Point sr) {

        super(newFuel);
        this.context = context;
        this.mSize=mSize;
        newFuel.setBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fastfuel), mSize, mSize, false));
        mBitmapFuel = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fastfuel), mSize, mSize, false);
        mSpawnRange=sr;
    }

    // Overriding spawn method
    public void spawn(){// Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    public void deSpawn(){// Choose two random values and place the apple

        location.x = -10;
        location.y = -10;
    }
    public void draw(Canvas canvas, Paint paint){
        super.draw(canvas, paint); // Draw the underlying fuel
        canvas.drawBitmap(mBitmapFuel, location.x * mSize, location.y * mSize, paint);

    }
    @Override
    public void setBitmap(Bitmap bitmap) {
        tempFuel.setBitmap(bitmap);
    }

    Point getLocation(){
        return location;
    }
}

