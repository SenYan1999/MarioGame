package com.mario.main;

import com.mario.game.Camera;
import com.mario.game.GameAction;
import com.mario.game.GameHandler;
import com.mario.game.InputHandler;
import com.mario.object.Mario;
import com.mario.view.ImageLoader;
import com.mario.view.MapHandler;
import com.mario.view.SelectMenu;
import com.mario.view.UIHandler;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game implements Runnable {
    private JFrame frame;
    private Thread thread;

    private final int WIDTH = 1268;
    private final int HEIGHT = 708;

    private UIHandler uiHandler;
    private GameHandler gameHandler;
    private InputHandler inputHandler = new InputHandler(this);

    private ImageLoader imageLoader;
    private SelectMenu selectMenu = SelectMenu.START_GAME;
    private Camera camera = new Camera();

    public int getWIDTH() {
        return WIDTH;
    }

    public SelectMenu getSelectMenu() {
        return selectMenu;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    private boolean running = false;
    private Status status;

    private int mapID = 0;

    public Game() throws IOException {

        // setting
        thread = new Thread(this);
        status = status.START_SCREEN;

        // define some handlers
        imageLoader = new ImageLoader();
        uiHandler = new UIHandler(this, WIDTH, HEIGHT);
        gameHandler = new GameHandler(this);

        frame = new JFrame("Super Mario");
        frame.add(uiHandler);

        // add key and mouse listener
        frame.addKeyListener(inputHandler);
        frame.addMouseListener(inputHandler);

        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        start();
    }

    public synchronized void start(){
        running = true;

        thread.start();
    }

    private void stop(){

    }

    private void updateCamera() {
        try {
            Mario mario = gameHandler.getMario();
            double marioVelocityX = mario.getVelX();
            double shiftAmount = 0;

            if (marioVelocityX > 0 && mario.getX() - 600 > camera.getX()) {
                shiftAmount = marioVelocityX;
            }
            camera.moveCam(shiftAmount, 0);
        }catch (NullPointerException e){}
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTricks = 60.0;
        double ns = 1000000000 / amountOfTricks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }

        stop();

    }

    private void tick(){
        gameHandler.updateLocation();
        gameHandler.checkCollisions(this);
        updateCamera();

        // end point
        if (status == Status.RUNNING || status == Status.MISSION_PASSED) {
            try {
                if (gameHandler.isEnd()) {
                    setStatus(Status.MISSION_PASSED);
                }
            } catch (Exception e) {
            }
        }
    }

    private void render(){
        uiHandler.repaint();
    }

    public void receiveInput(GameAction gameAction) throws IOException {
        if(status == Status.START_SCREEN){
            if(gameAction == GameAction.GO_UP){
                selectOption(true);
            }
            else if(gameAction == GameAction.GO_DOWN){
                selectOption(false);
            }
            else if(gameAction == GameAction.SELECT && selectMenu == SelectMenu.ABOUT_PAGE){
                setStatus(Status.ABOUT_SCREEN);
            }
            else if(gameAction == GameAction.SELECT && selectMenu == SelectMenu.HELP_PAGE){
                setStatus(Status.HELP_SCREEN);
            }
            else if(gameAction == GameAction.SELECT && selectMenu == SelectMenu.START_GAME){
                setStatus(Status.MAP_SELECTION);
            }
        }
        else if(status == Status.MAP_SELECTION){
            if(gameAction == GameAction.GO_UP){
                mapID = gameHandler.getMapHandler().changeSelectedMap(mapID, true);
            }
            else if(gameAction == GameAction.GO_DOWN){
                mapID = gameHandler.getMapHandler().changeSelectedMap(mapID, false);
            }
            else if(gameAction == GameAction.SELECT){
                gameHandler.selectMapViaKeyboard(mapID);
            }
        }
        else if (status == Status.RUNNING) {
            Mario mario = gameHandler.getMario();
            if (gameAction == GameAction.JUMP) {
                mario.jump(this);
            } else if (gameAction == GameAction.M_RIGHT) {
                mario.move(true, camera);
            } else if (gameAction == GameAction.M_LEFT) {
                mario.move(false, camera);
            } else if (gameAction == GameAction.ACTION_COMPLETED) {
                mario.setVelX(0);
            }
        }
        else if (status == Status.MISSION_PASSED){
            if (gameAction == GameAction.SELECT){
                setStatus(Status.MAP_SELECTION);
                camera.shakeCamera();
            }
        }

        if(gameAction == GameAction.GO_TO_START_SCREEN){
            setStatus(Status.START_SCREEN);
        }
    }

    private void selectOption(boolean selectUP){
        selectMenu = selectMenu.select(selectUP);
    }

    public Graphics getGraphics(){
        return frame.getGraphics();
    }

    public Status getStatus(){return status;}

    public void setStatus(Status status){this.status = status;}

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public int getMapID(){return mapID;}

    public void shakeCamera(){
        camera.shakeCamera();
    }

    public Point getCameraLocation() {
        return new Point((int)camera.getX(), (int)camera.getY());
    }

    public static void main(String args[]) throws IOException {
        new Game();
    }

}
