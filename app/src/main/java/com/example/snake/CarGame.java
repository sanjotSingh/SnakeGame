package com.example.snake;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

public class CarGame extends SurfaceView implements Runnable {

    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    // for playing sound effects
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;


    private SurfaceHolder mSurfaceHolder;


    private Renderer mRenderer;
    private Car mCar;
    // And an apple
    private Fuel mApple;

    private Button mButton;

    /*
    private Bitmap buttonBitmap;
    */
    private Rect buttonRect;


    private boolean isPaused = true;
    private final int buttonWidth = 200; // Adjust button width as needed
    private final int buttonHeight = 100; // Adjust button height as needed
    int blockSize;
    // This is the constructor method that gets called
    // from CarActivity
    public CarGame(Context context, Point size) {
        super(context);

        // Work out how many pixels each block is
        blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        setUpSound(context);



        // Call the constructors of our three game objects
        mApple = new Fuel(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        mCar = new Car(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);


        mButton = new Button(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize, buttonWidth,buttonHeight);

        //load Renderer
        mSurfaceHolder = getHolder();

        mRenderer = new Renderer(context,mSurfaceHolder);

        // Set button position (example: center of the screen)
        float buttonX = mButton.x;
        float buttonY = mButton.y;
        buttonRect = new Rect((int)mButton.x, (int)mButton.y, (int)mButton.x + buttonWidth, (int)mButton.y + buttonHeight);

    }

    public void setUpSound(Context context){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            // Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Error
        }
    }

    // Called to start a new game
    public void newGame() {

        // reset the snake
        mCar.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mApple.spawn();

        // Reset the mScore
        mScore = 0;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
    }


    // Handles the game loop
    @Override
    public void run() {
        while (mPlaying) {
            if(!mPaused) {
                // Update 10 times a second
                if (updateRequired()) {
                    update();
                }
            }

            mRenderer.draw(mApple, mCar,mButton, mScore, mPaused);
        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Run at 10 frames per second
        final long TARGET_FPS = 10;
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }



    // Update all the game objects
    public void update() {

        // Move the snake
        mCar.move();

        // Did the head of the snake eat the apple?
        if(mCar.checkDinner(mApple.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mApple.spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);
        }

        // Did the snake die?
        if (mCar.detectDeath()) {
            // Pause the game ready to start again
            mSP.play(mCrashID, 1, 1, 0, 0, 1);

            mPaused =true;
        }

    }


    // Do all the drawing


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                if (mPaused) {
                    mPaused = false;
                    newGame();
                }
                // Check if touch event occurred within the button bounds
                else if(buttonRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    // Toggle game pause state
                    isPaused = !isPaused;
                    if (isPaused) {
                        // Game paused, perform pause actions
                        pause();
                    } else {
                        // Game resumed, perform resume actions
                        resume();
                    }
                    return true; // Consume touch event
                }
                    // Let the Car class handle the input
                mCar.switchHeading(motionEvent);

                break;



            default:
                break;

        }
        return true;
    }


    // Stop the thread
    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Error
            Log.e("Error:", "joining thread");
        }
    }


    // Start the thread
    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void togglePause() {
        mPaused = !mPaused;
    }

    public boolean isPaused() {
        return mPaused;
    }
}