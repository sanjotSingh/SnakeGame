package com.example.snake;

import android.content.Context;

public class AudioContext {
    private AudioPattern audioPattern;

    public AudioContext(Context context){
        audioPattern = new SoundPoolStrategy(context);

    }

    public void setAudioPattern(AudioPattern audioPattern){
        this.audioPattern = audioPattern;

    }

    public void playEatSound(){
        audioPattern.playEatSound();

    }

    public void playCrashSound(){
        audioPattern.playCrashSound();

    }

}