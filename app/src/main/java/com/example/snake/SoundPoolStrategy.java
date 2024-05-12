package com.example.snake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

public class SoundPoolStrategy implements AudioPattern{
    private SoundPool mSP;
    private int mEat_Id = -1;
    private int mCrash_Id = -1;

    public SoundPoolStrategy(Context context) {
        setUpSound(context);
    }

    public void setUpSound(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
            mSP = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(attributes).build();

        }else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;

            //Prepare the sounds in memory
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_Id = mSP.load(descriptor, 0);

            descriptor = assetManager.openFd("snake_death.ogg");
            mCrash_Id = mSP.load(descriptor, 0);

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void playEatSound(){
        mSP.play(mEat_Id, 1, 1, 0, 0, 1);

    }

    @Override
    public void playCrashSound(){
        mSP.play(mCrash_Id, 1, 1, 0, 0, 1);
    }

}
