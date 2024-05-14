package com.example.snake;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Renderer extends SurfaceView {
    private CarGame carGame;
    // Objects for drawing
    private Canvas mCanvas;
    private Paint mPaint;
    private SurfaceHolder mSurfaceHolder;


    public Renderer(Context context, SurfaceHolder mSurfaceHolder) {
        super(context);
        this.mSurfaceHolder=mSurfaceHolder;
        init();
    }


    private void init() {
        // Initialize the drawing objects
        mPaint = new Paint();
    }





    public void draw(PlainFuel mPlainFuel,FastFuel mFastFuel,Obstacle mObstacle, Car mCar, Button mButton, int mScore, Boolean mPaused, Boolean gameOver,int mHighScore) {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();


            // Set Background color
            mCanvas.drawColor(Color.argb(255,0, 0, 0));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(50);

            //set Font
            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/TacOne-Regular.ttf");
            mPaint.setTypeface(font);



            // Draw the score
            mCanvas.drawText("Score: " + mScore, 20, 80, mPaint);

            // Draw the fuel, the snake and the pause button
            mPlainFuel.draw(mCanvas, mPaint);
            mFastFuel.draw(mCanvas, mPaint);
            mObstacle.draw(mCanvas, mPaint);

            mCar.draw(mCanvas, mPaint);
            mButton.draw(mCanvas, mPaint);

            // Draw some text while paused

            if (gameOver) {

                // Load the background image (assuming you have an image named "backgroundImage")
                Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.accident);

                // Draw the background image
                mCanvas.drawBitmap(backgroundImage, 0, 0, mPaint);

                // Set the size and color of the game over text
                mPaint.setColor(Color.argb(255, 255, 255, 255));
                mPaint.setTextSize(250);

                // Draw the game over message
                mCanvas.drawText("Game Over", 550, 400, mPaint);

                // Draw the final score
                mPaint.setTextSize(50);
                mCanvas.drawText("Click anywhere to restart", 550, 500, mPaint);
                mCanvas.drawText("Final Score: " + mScore, 550, 600, mPaint);
                mCanvas.drawText("High Score: " + mHighScore, 550, 700, mPaint);


            } else if(mPaused){

                renderMenu();
            }

            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }


    public void renderMenu(){

        // Load the background image (assuming you have an image named "backgroundImage")
        Bitmap backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.steamengine);

        // Draw the background image
        mCanvas.drawBitmap(backgroundImage, 0, 0, mPaint);


        // Set the size and color of the pause text
        mPaint.setColor(Color.argb(255, 255, 255, 255));
        mPaint.setTextSize(250);

        // Draw the message
        // We will give this an international upgrade soon
        mCanvas.drawText("Tap To Play!", 550, 500, mPaint);

        mPaint.setTextSize(50);

    }


}
