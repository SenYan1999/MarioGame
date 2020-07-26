package com.mario.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {

    private BufferedImage marioForms;
    private BufferedImage brickAnimation;

    public ImageLoader() throws IOException {
        marioForms = load_image("mario-forms.png");
        brickAnimation = load_image("brick-animation.png");
    }

    public BufferedImage load_image(String path) throws IOException {
        BufferedImage imageToReturn = null;
        try{
            imageToReturn = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/" + path));
        } catch (IOException e){
            e.printStackTrace();
        }

        return imageToReturn;
    }

    public BufferedImage getSubImage(BufferedImage image, int col, int row, int w, int h){
        return image.getSubimage((col - 1) * 48, (row - 1) * 48, w, h);
    }

    public BufferedImage[] getLeftFrames(int marioForm){
        BufferedImage[] leftFrames = new BufferedImage[5];
        int col = 1;
        int width = 52, height = 48;

        if(marioForm == 1) { //super mario
            col = 4;
            width = 48;
            height = 96;
        }
        else if(marioForm == 2){ //fire mario
            col = 7;
            width = 48;
            height = 96;
        }

        for(int i = 0; i < 5; i++){
            leftFrames[i] = marioForms.getSubimage((col-1)*width, (i)*height, width, height);
        }
        return leftFrames;
    }

    public BufferedImage[] getRightFrames(int marioForm){
        BufferedImage[] rightFrames = new BufferedImage[5];
        int col = 2;
        int width = 52, height = 48;

        if(marioForm == 1) { //super mario
            col = 5;
            width = 48;
            height = 96;
        }
        else if(marioForm == 2){ //fire mario
            col = 8;
            width = 48;
            height = 96;
        }

        for(int i = 0; i < 5; i++){
            rightFrames[i] = marioForms.getSubimage((col-1)*width, (i)*height, width, height);
        }
        return rightFrames;
    }

    public BufferedImage[] getBrickFrames() {
        BufferedImage[] frames = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            frames[i] = brickAnimation.getSubimage(i*105, 0, 105, 105);
        }
        return frames;
    }

}
