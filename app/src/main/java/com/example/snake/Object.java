package com.example.snake;
import android.graphics.Point;

public class Object {
    Point location;

    Point getLocation(){
        return location;
    }

    void setLocation(int x, int y){
        this.location.x = x;
        this.location.y = y;
    }

}
