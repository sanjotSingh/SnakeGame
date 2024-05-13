package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;


abstract class FuelDecorator implements Fuel,Drawable{
    protected Fuel tempFuel;
    public FuelDecorator(Fuel newFuel){

        this.tempFuel = newFuel;

    }

    public void spawn(){// Choose two random values and place the apple
        tempFuel.spawn();
    }


    public void draw(Canvas canvas, Paint paint){
        tempFuel.draw(canvas, paint);

    }
    @Override
    public void setBitmap(Bitmap bitmap) {
        tempFuel.setBitmap(bitmap);
    }
}
