package com.mario.game;

import com.mario.main.Game;
import com.mario.main.Status;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class InputHandler implements KeyListener, MouseListener {
    private Game game;

    public InputHandler(Game game){
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        Status status = game.getStatus();
        GameAction gameAction = GameAction.NO_ACTION;

        if(keyCode == KeyEvent.VK_UP){
            if(status == Status.START_SCREEN || status == Status.MAP_SELECTION){
                gameAction = GameAction.GO_UP;
            }
            else if(status == Status.RUNNING){
                gameAction = GameAction.JUMP;
            }
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            if (status == Status.START_SCREEN || status == Status.MAP_SELECTION) {
                gameAction = GameAction.GO_DOWN;
            }
        }
        else if(keyCode == KeyEvent.VK_RIGHT) {
            gameAction = GameAction.M_RIGHT;
        }
        else if(keyCode == KeyEvent.VK_LEFT) {
            gameAction = GameAction.M_LEFT;
        }
        else if(keyCode == KeyEvent.VK_ENTER){
            gameAction = GameAction.SELECT;
        }
        else if(keyCode == KeyEvent.VK_ESCAPE){
            if(status == Status.RUNNING || status == Status.PAUSED){
                gameAction = GameAction.PAUSE_RESUME;
            }
            else
                gameAction = GameAction.GO_TO_START_SCREEN;
        }
        else if(keyCode == KeyEvent.VK_SPACE){
            gameAction = GameAction.FIRE;
        }

        try {
            notifyGame(gameAction);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    private void notifyGame(GameAction gameAction) throws IOException {
        if(gameAction != GameAction.NO_ACTION){
            game.receiveInput(gameAction);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT){
            try {
                notifyGame(GameAction.ACTION_COMPLETED);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
