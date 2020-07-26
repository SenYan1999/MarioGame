package com.mario.game;

import com.mario.object.*;
import com.mario.view.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

class MapCreator {

    private ImageLoader imageLoader;

    private BufferedImage backgroundImage;
    private BufferedImage ordinaryBrick, surpriseBrick, groundBrick, pipe;
    private BufferedImage goombaLeft, goombaRight, koopaLeft, koopaRight, endFlag;


    MapCreator(ImageLoader imageLoader) throws IOException {

        this.imageLoader = imageLoader;
        BufferedImage sprite = imageLoader.load_image("sprite.png");

        this.backgroundImage = imageLoader.load_image("background.png");
        this.ordinaryBrick = imageLoader.getSubImage(sprite, 1, 1, 48, 48);
        this.surpriseBrick = imageLoader.getSubImage(sprite, 2, 1, 48, 48);
        this.groundBrick = imageLoader.getSubImage(sprite, 2, 2, 48, 48);
        this.pipe = imageLoader.getSubImage(sprite, 3, 1, 96, 96);
        this.goombaLeft = imageLoader.getSubImage(sprite, 2, 4, 48, 48);
        this.goombaRight = imageLoader.getSubImage(sprite, 5, 4, 48, 48);
        this.koopaLeft = imageLoader.getSubImage(sprite, 1, 3, 48, 64);
        this.koopaRight = imageLoader.getSubImage(sprite, 4, 3, 48, 64);
        this.endFlag = imageLoader.getSubImage(sprite, 5, 1, 48, 48);

    }

    public Map createMap(String mapPath, double timeLimit) throws IOException {
        BufferedImage mapImage = imageLoader.load_image(mapPath);

        if (mapImage == null) {
            System.out.println("Given path is invalid...");
            return null;
        }

        Map createdMap = new Map(timeLimit, backgroundImage);
        String[] paths = mapPath.split("/");
        createdMap.setPath(paths[paths.length - 1]);

        int pixelMultiplier = 48;

        int mario = new Color(160, 160, 160).getRGB();
        int ordinaryBrick = new Color(0, 0, 255).getRGB();
        int groundBrick = new Color(255, 0, 0).getRGB();
        int end = new Color(160, 0, 160).getRGB();

        for (int x = 0; x < mapImage.getWidth(); x++) {
            for (int y = 0; y < mapImage.getHeight(); y++) {

                int currentPixel = mapImage.getRGB(x, y);
                int xLocation = x * pixelMultiplier;
                int yLocation = y * pixelMultiplier;

                if (currentPixel == ordinaryBrick) {
                    Brick brick = new OrdinaryBrick(xLocation, yLocation, this.ordinaryBrick);
                    createdMap.addBrick(brick);
                }
                else if(currentPixel == groundBrick){
                    Brick brick = new GroundBrick(xLocation, yLocation, this.groundBrick);
                    createdMap.addGroundBrick(brick);
                }
                else if(currentPixel == mario){
                    Mario marioObject = new Mario(xLocation, yLocation);
                    createdMap.setMario(marioObject);
                }
                else if(currentPixel == end){
                    EndFlag endPoint = new EndFlag(xLocation+24, yLocation, endFlag);
                    createdMap.setEndFlag(endPoint);
                }

            }
        }

        System.out.println("Map is created..");
        return createdMap;
    }
}
