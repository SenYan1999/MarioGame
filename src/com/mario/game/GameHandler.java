package com.mario.game;

import com.mario.main.Game;
import com.mario.main.Status;
import com.mario.object.Brick;
import com.mario.object.GameObject;
import com.mario.object.Mario;
import com.mario.view.MapHandler;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameHandler {

    private MapHandler mapHandler;
    private Map map;
    private Game game;

    public MapHandler getMapHandler() {
        return mapHandler;
    }

    public void updateLocation(){
        if(map != null)
            map.updateLocations();
    }

    public GameHandler(Game game) throws IOException {
        mapHandler = new MapHandler();
        this.game = game;
    }

    public void selectMapViaKeyboard(int idx) throws IOException {
        String path = mapHandler.selectMap(idx);
        createMap(path);
    }

    public void drawMap(Graphics2D g2){
        map.drawMap(g2);
    }

    public Mario getMario(){
        return map.getMario();
    }

    public void createMap(String path) throws IOException {
        MapCreator mapCreator = new MapCreator(game.getImageLoader());
        map = mapCreator.createMap("maps/" + path, 400);

        game.setStatus(Status.RUNNING);
    }

    public void checkCollisions(Game engine) {
        if (map == null) {
            return;
        }

        checkBottomCollisions(engine);
        checkTopCollisions(engine);
        checkMarioHorizontalCollision(engine);
    }

    private void checkBottomCollisions(Game engine) {
        Mario mario = getMario();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        Rectangle marioBottomBounds = mario.getBottomBounds();

        if (!mario.isJumping())
            mario.setFalling(true);

        for (Brick brick : bricks) {
            Rectangle brickTopBounds = brick.getTopBounds();
            if (marioBottomBounds.intersects(brickTopBounds)) {
                mario.setY(brick.getY() - mario.getDimension().height + 1);
                mario.setFalling(false);
                mario.setVelY(0);
            }
        }

        if (mario.getY() + mario.getDimension().height >= map.getBottomBorder()) {
            mario.setY(map.getBottomBorder() - mario.getDimension().height);
            mario.setFalling(false);
            mario.setVelY(0);
        }

    }

    private void checkTopCollisions(Game engine) {
        Mario mario = getMario();
        ArrayList<Brick> bricks = map.getAllBricks();

        Rectangle marioTopBounds = mario.getTopBounds();
        for (Brick brick : bricks) {
            Rectangle brickBottomBounds = brick.getBottomBounds();
            if (marioTopBounds.intersects(brickBottomBounds)) {
                mario.setVelY(0);
                mario.setY(brick.getY() + brick.getDimension().height);
            }
        }
    }

    private void checkMarioHorizontalCollision(Game engine){
        Mario mario = getMario();
        ArrayList<Brick> bricks = map.getAllBricks();
        ArrayList<GameObject> toBeRemoved = new ArrayList<>();

        boolean marioDies = false;
        boolean toRight = mario.getToRight();

        Rectangle marioBounds = toRight ? mario.getRightBounds() : mario.getLeftBounds();

        for (Brick brick : bricks) {
            Rectangle brickBounds = !toRight ? brick.getRightBounds() : brick.getLeftBounds();
            if (marioBounds.intersects(brickBounds)) {
                mario.setVelX(0);
                if(toRight)
                    mario.setX(brick.getX() - mario.getDimension().width);
                else
                    mario.setX(brick.getX() + brick.getDimension().width);
            }
        }

        if (mario.getX() <= engine.getCameraLocation().getX() && mario.getVelX() < 0) {
            mario.setVelX(0);
            mario.setX(engine.getCameraLocation().getX());
        }
    }

    public int passMission() {
        if(getMario().getX() >= map.getEndFlag().getX() && !map.getEndFlag().isTouched()){
            map.getEndFlag().setTouched(true);
            int height = (int)getMario().getY();
            return height * 2;
        }
        else
            return -1;
    }

    public boolean isEnd(){
        return getMario().getX() >= map.getEndFlag().getX() + 320;
    }
}
