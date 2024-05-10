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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CarGame extends SurfaceView implements Runnable {

    // Previous high score
    private int mHighScore = 0;
    private static final String HIGH_SCORE_FILE = "high_score.txt";


    // Objects for the game loop/thread
    private Thread mThread = null;
    // Control pausing between updates
    private long mNextFrameTime;
    // Is the game currently playing and or paused?
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    private Audio mAudio;

    // for playing sound effects
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 20;
    private int mNumBlocksHigh;

    // How many points does the player have
    private int mScore;


    private SurfaceHolder mSurfaceHolder;


    private Renderer mRenderer;
    private Car mCar;
    // And an apple
    private PlainFuel mPlainFuel;

    private Obstacle mObstacle;

    private Button mButton;

    /*
    private Bitmap buttonBitmap;
    */
    private Rect buttonRect;


    private boolean isPaused = true;
    private boolean gameOver = false;

    private final int buttonWidth = 200; // Adjust button width as needed
    private final int buttonHeight = 100; // Adjust button height as needed

    // Add a new field for the next obstacle move time
    private long mNextObstacleSpawnTime;

    // Define a constant for the interval between each obstacle position update
    private final long OBSTACLE_SPAWN_INTERVAL = 3000; // Adjust the interval as needed (in milliseconds)

    int blockSize;
    long speed = 3;

    public long getSpeed(){
        return speed;
    }
    public void setSpeed(long speed){
        this.speed = speed;
    }
    // This is the constructor method that gets called
    // from CarActivity
    public CarGame(Context context, Point size) {
        super(context);

        // Initialize mNextObstacleMoveTime with current time + OBSTACLE_MOVE_INTERVAL
        mNextObstacleSpawnTime = System.currentTimeMillis() + OBSTACLE_SPAWN_INTERVAL;

        mAudio = new Audio(context);

        // Work out how many pixels each block is
        blockSize = size.x / NUM_BLOCKS_WIDE;
        // How many blocks of the same size will fit into the height
        mNumBlocksHigh = size.y / blockSize;

        setUpSound(context);


        mCar = new Car(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        // Call the constructors of our three game objects
        mPlainFuel = new PlainFuel(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize,mCar);

        mObstacle = new Obstacle(context,
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
        gameOver = false;

        // reset the snake
        mCar.reset(NUM_BLOCKS_WIDE, mNumBlocksHigh);

        // Get the apple ready for dinner
        mPlainFuel.spawn();

        mObstacle.spawn();


        // Reset the mScore
        mScore = 0;

        // Setup mNextFrameTime so an update can triggered
        mNextFrameTime = System.currentTimeMillis();
        // Load high score
        loadHighScore();
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


            mRenderer.draw(mPlainFuel, mObstacle, mCar,mButton, mScore, mPaused, gameOver,mHighScore);

        }
    }


    // Check to see if it is time for an update
    public boolean updateRequired() {

        // Snake speed
        // There are 1000 milliseconds in a second
        final long MILLIS_PER_SECOND = 1000;

        // Are we due to update the frame
        if(mNextFrameTime <= System.currentTimeMillis()){
            // Tenth of a second has passed

            // Setup when the next update will be triggered
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / speed;

            // Return true so that the update and draw
            // methods are executed
            return true;
        }

        return false;
    }


    // Check to see if it is time to spawn a new obstacle
    public boolean obstacleSpawnRequired() {
        final long OBSTACLE_SPAWN_INTERVAL = 5000; // Adjust the interval as needed (in milliseconds)

        // Check if the current time exceeds the next obstacle spawn time
        if (System.currentTimeMillis() >= mNextObstacleSpawnTime) {
            // It's time to spawn a new obstacle
            // Update the next obstacle spawn time
            mNextObstacleSpawnTime = System.currentTimeMillis() + OBSTACLE_SPAWN_INTERVAL;
            return true;
        }

        // It's not yet time to spawn a new obstacle
        return false;
    }



    // Update all the game objects
    public void update() {

        // Move the snake
        mCar.move();

        // Spawn obstacles if required
        if (obstacleSpawnRequired()) {
            mObstacle.spawn();
        }

        // Did the head of the snake eat the apple?
        if(mCar.checkNitro(mPlainFuel.getLocation())){
            // This reminds me of Edge of Tomorrow.
            // One day the apple will be ready!
            mPlainFuel.spawn();

            // Add to  mScore
            mScore = mScore + 1;

            // Play a sound
            mAudio.playEatSound();
            speed = (long)(speed+0.1);// When the snake eats the apple
        }

        // Check collision with obstacle
        if (mCar.checkNitro(mObstacle.getLocation())) {
            mObstacle.spawn(); // Respawn obstacle
            // Add method to adjust snake speed or any other actions related to hitting obstacle
            mAudio.playCrashSound();
            gameOver = true;
            mPaused = true;
            updateHighScore();
        }

        // Check for game over - Did the snake die?
        if (mCar.detectDeath()) {
            // Pause the game ready to start again
            mAudio.playCrashSound(); // When the snake crashes
            gameOver = true;
            mPaused = true;
            updateHighScore();
        }

    }


    // Update the position of the obstacle continuously
    private void updateObstaclePosition() {
        // Move the obstacle to a random position
        if (System.currentTimeMillis() >= mNextObstacleSpawnTime) {
            mObstacle.spawn(); // Respawn obstacle at a random position
            mNextObstacleSpawnTime = System.currentTimeMillis() + OBSTACLE_SPAWN_INTERVAL;
        }
    }


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

    // Load the high score from file
    private void loadHighScore() {
        File file = new File( HIGH_SCORE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String scoreStr = reader.readLine();
                if (scoreStr != null && !scoreStr.isEmpty()) {
                    mHighScore = Integer.parseInt(scoreStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Save the high score to file
    private void saveHighScore() {
        File file = new File( HIGH_SCORE_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Integer.toString(mHighScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Called when the game ends to update high score if necessary
    private void updateHighScore() {
        if (mScore > mHighScore) {
            mHighScore = mScore;
            saveHighScore();
        }
    }
}