package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import java.io.IOException;
public class Audio {

    private AudioContext audioContext;

    
    public Audio(Context context) {
        audioContext = new AudioContext(context);
    }
    public void setAudioPattern(AudioPattern audioPattern) {
        audioContext.setAudioPattern(audioPattern);
    }

    public void playEatSound() {
        audioContext.playEatSound();
    }
    public void playCrashSound() {
        audioContext.playCrashSound();
    }

}
  /*  private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;

    public Audio(Context context) {
        setUpSound(context);
    }

    private void setUpSound(Context context) {
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

    public void playEatSound() {
        mSP.play(mEat_ID, 1, 1, 0, 0, 1);
    }

    public void playCrashSound() {
        mSP.play(mCrashID, 1, 1, 0, 0, 1);
    }
}
*/