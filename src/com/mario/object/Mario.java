package com.mario.object;

import com.mario.game.Camera;
import com.mario.view.Animation;
import com.mario.view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mario extends GameObject {
    private MarioForm marioForm;
    private boolean toRight = true;

    public Mario(double x, double y) throws IOException {
        super(x, y, null);
        setDimension(new Dimension(48, 48));

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getLeftFrames(MarioForm.SMALL);
        BufferedImage[] rightFrames = imageLoader.getRightFrames(MarioForm.SMALL);


        Animation animation = new Animation(leftFrames, rightFrames);
        marioForm = new MarioForm(animation, false, false);
        setSkin(marioForm.getCurrentStyle(toRight, false, false));

    }

    @Override
    public void draw(Graphics g){
        boolean movingInX = (getVelX() != 0);
        boolean movingInY = (getVelY() != 0);

        setSkin(marioForm.getCurrentStyle(toRight, movingInX, movingInY));

        super.draw(g);
    }

    public void move(boolean toRight, Camera camera) {
        if(toRight){
            setVelX(5);
        }
        else if(camera.getX() < getX()){
            setVelX(-5);
        }

        this.toRight = toRight;
    }

    public void jump() {
        if(!isJumping() && !isFalling()){
            setJumping(true);
            setVelY(10);
        }
    }

    public boolean getToRight() {
        return toRight;
    }
}
