package com.mario.object;

import com.mario.view.ImageLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class OrdinaryBrick extends Brick {

    public OrdinaryBrick(double x, double y, BufferedImage style) throws IOException {
        super(x, y, style);
        setAnimation();

        setBreakable(true);
        setEmpty(true);
    }

    private void setAnimation() throws IOException {
        ImageLoader imageLoader = new ImageLoader();
        BufferedImage[] leftFrames = imageLoader.getBrickFrames();

    }
}
