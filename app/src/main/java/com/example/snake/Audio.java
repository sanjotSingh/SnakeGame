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