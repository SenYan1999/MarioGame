package com.mario.object;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    private double x, y;
    private double velX, velY;
    private double gravityAcc;

    private boolean falling, jumping;

    private Dimension dimension;

    private BufferedImage skin;

    public GameObject(double x, double y, BufferedImage skin){
        setLocation(x, y);
        setSkin(skin);

        // define dimension
        if(skin != null)
            setDimension(new Dimension(skin.getWidth(), skin.getHeight()));

        setVelX(0);
        setVelY(0);
        setGravityAcc(0.38);
        jumping = false;
        falling = true;
    }

    public void draw(Graphics g){
        BufferedImage skin = getSkin();

        if(skin != null)
            g.drawImage(skin, (int)x, (int)y, null);
    }

    public void updateLocation(){
        if(jumping && velY <= 0){
            jumping = false;
            falling = true;
        }
        else if(jumping){
            velY = velY - gravityAcc;
            y = y - velY;
        }

        if(falling){
            y = y + velY;
            velY = velY + gravityAcc;
            if(y > 576){
                y = 578;
                velY = 0;
                falling = false;
            }
        }

        x = x + velX;
    }

    public void setLocation(double x, double y){
        setX(x);
        setY(y);
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public BufferedImage getSkin(){
        return skin;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public double getGravityAcc() {
        return gravityAcc;
    }

    public void setGravityAcc(double gravityAcc) {
        this.gravityAcc = gravityAcc;
    }

    public boolean isFalling(){return falling;}
    public boolean isJumping(){return jumping;}

    public void setFalling(boolean falling){this.falling = falling;}
    public void setJumping(boolean jumping){this.jumping = jumping;}

    public Rectangle getTopBounds(){
        return new Rectangle((int)x+dimension.width/6, (int)y, 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getBottomBounds(){
        return new Rectangle((int)x+dimension.width/6, (int)y + dimension.height/2, 2*dimension.width/3, dimension.height/2);
    }

    public Rectangle getLeftBounds(){
        return new Rectangle((int)x, (int)y + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getRightBounds(){
        return new Rectangle((int)x + 3*dimension.width/4, (int)y + dimension.height/4, dimension.width/4, dimension.height/2);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, dimension.width, dimension.height);
    }

}
