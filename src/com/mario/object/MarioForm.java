package com.mario.object;


import com.mario.view.Animation;
import com.mario.view.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MarioForm {

    public static final int SMALL = 0, SUPER = 1, FIRE = 2;

    private Animation animation;
    private boolean isSuper, isFire; //note: fire form has priority over super form
    private BufferedImage fireballStyle;

    public MarioForm(Animation animation, boolean isSuper, boolean isFire) throws IOException {
        this.animation = animation;
        this.isSuper = isSuper;
        this.isFire = isFire;

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage fireball = imageLoader.load_image("sprite.png");
        fireballStyle = imageLoader.getSubImage(fireball, 3, 4, 24, 24);
    }

    public BufferedImage getCurrentStyle(boolean toRight, boolean movingInX, boolean movingInY){

        BufferedImage style;

        if(movingInY && toRight){
            style = animation.getRightFrames()[0];
        }
        else if(movingInY){
            style = animation.getLeftFrames()[0];
        }
        else if(movingInX){
            style = animation.animate(5, toRight);
        }
        else {
            if(toRight){
                style = animation.getRightFrames()[1];
            }
            else
                style = animation.getLeftFrames()[1];
        }

        return style;
    }
}
