package com.mario.game;

public class Camera {

    private double x, y;
    private int frameNumber;
    private boolean shaking;

    public Camera(){
        this.x = 0;
        this.y = 0;
        this.frameNumber = 25;
        this.shaking = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void shakeCamera() {
        this.x = -10;
        this.y = 0;
        this.frameNumber = 25;
        this.shaking = false;
    }

    public void moveCam(double xAmount, double yAmount){
        if(shaking && frameNumber > 0){
            int direction = (frameNumber%2 == 0)? 1 : -1;
            x = x + 4 * direction;
            frameNumber--;
        }
        else{
            x = x + xAmount;
            y = y + yAmount;
        }

        if(frameNumber < 0)
            shaking = false;
    }
}
