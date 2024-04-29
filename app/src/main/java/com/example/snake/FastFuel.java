package com.example.snake;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class FastFuel extends FuelDecorator{
    public FastFuel(Fuel decoratedFuel) {
        super(decoratedFuel);
    }

    // Overriding spawn method
    @Override
    public void spawn() {
        // Additional logic
        // Call getLocation method from decoratedFuel
        Point location = ((PlainFuel) decoratedFuel).getLocation();
        // Additional logic using location
        // Call spawn method of decoratedFuel
        super.spawn();
    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        decoratedFuel.draw(canvas, paint);

    }
}
