package com.example.snake;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
public class Renderer extends SurfaceView {
    private SnakeGame snakeGame;
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




    public void draw(Apple mApple, Snake mSnake, Button mButton, int mScore,Boolean mPaused) {
        // Get a lock on the mCanvas
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();


            // Fill the screen with a color
            mCanvas.drawColor(Color.argb(255,100,200,100));

            // Set the size and color of the mPaint for the text
            mPaint.setColor(Color.argb(255, 0, 0, 0));
            mPaint.setTextSize(50);

            Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/TacOne-Regular.ttf");
            mPaint.setTypeface(font);



            // Draw the score
            mCanvas.drawText("" + mScore, 20, 80, mPaint);

            // Draw the apple, the snake and the pause button
            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            mButton.draw(mCanvas, mPaint);

            // Draw some text while paused
            if(mPaused){

                // Set the size and color of the mPaint for the text
                mPaint.setColor(Color.argb(255, 0, 0, 0));
                mPaint.setTextSize(250);

                // Draw the message
                // We will give this an international upgrade soon
                mCanvas.drawText("Tap To Play!", 200, 700, mPaint);

                mPaint.setTextSize(50);
                // Inputting names
                mCanvas.drawText("Arshmit Bains", 1880, 80, mPaint);
                mCanvas.drawText("Sanjot Chandi", 1540, 80, mPaint);
            }


            // Unlock the mCanvas and reveal the graphics for this frame
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }





}
