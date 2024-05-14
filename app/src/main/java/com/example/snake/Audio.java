package com.example.snake;

import android.content.Context;
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