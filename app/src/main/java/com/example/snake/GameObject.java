package com.example.snake;
import android.graphics.Point;

public class GameObject {
    Point location;

    GameObject(){
        location = new Point(); // Initialize the location in the constructor
    }

    Point getLocation(){
        return location;
    }

    void setLocation(int x, int y){
        this.location.x = x;
        this.location.y = y;
    }
}
