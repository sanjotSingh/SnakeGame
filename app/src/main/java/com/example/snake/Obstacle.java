package com.example.snake;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

public class Obstacle {
    private Point location = new Point(); // Location of the obstacle
    private int mSize; // Size of each block
    private Point mSpawnRange;

    // An image to represent the obstacle
    private Bitmap mBitmapObstacle;

    public Obstacle(Context context, Point sr, int s){
        super();
        // Make a note of the passed in spawn range
        mSpawnRange = sr;
        // Make a note of the size of an apple
        mSize = s;
        // Hide the apple off-screen until the game starts
        location.x = -10;

        // Load the image to the bitmap
        mBitmapObstacle = BitmapFactory.decodeResource(context.getResources(), R.drawable.tnt);

        // Resize the bitmap
        mBitmapObstacle = Bitmap.createScaledBitmap(mBitmapObstacle, s, s, false);
    }

    // This is called every time an apple is eaten
    public void spawn(){
        // Choose two random values and place the apple
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    // Getter method for obstacle location
    public Point getLocation() {
        return location;
    }

    // Draw the obstacle
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapObstacle,
                location.x * mSize, location.y * mSize, paint);
    }
}
