package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;


abstract class FuelDecorator implements Fuel{
    protected Fuel decoratedFuel;
    public FuelDecorator(Fuel decoratedFuel){

        this.decoratedFuel = decoratedFuel;

    }

    public void spawn(){// Choose two random values and place the apple
        decoratedFuel.spawn();
    }


    public void draw(Canvas canvas, Paint paint){
        decoratedFuel.draw(canvas, paint);

    }
    @Override
    public void setBitmap(Bitmap bitmap) {
        decoratedFuel.setBitmap(bitmap);
    }
}
