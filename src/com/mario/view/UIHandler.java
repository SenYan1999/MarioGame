package com.mario.view;

import com.mario.main.Game;
import com.mario.main.Status;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UIHandler extends JPanel {

    private Game game;

    // some components of icon
    private BufferedImage startScreenImage, aboutScreenImage, helpScreenImage, gameOverScreen;
    private BufferedImage heartIcon;
    private BufferedImage selectIcon;
    private BufferedImage coinIcon;
    private Font gameFont;

    // game status
    private Status status;

    public UIHandler(Game game, int width, int height) throws IOException {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));

        this.game = game;
        this.status = game.getStatus();
        ImageLoader loader = game.getImageLoader();

        BufferedImage sprite = loader.load_image("sprite.png");
        this.startScreenImage = loader.load_image("start-screen.png");
        this.aboutScreenImage = loader.load_image("about-screen.png");
        this.helpScreenImage = loader.load_image("help-screen.png");
        this.gameOverScreen = loader.load_image("game-over.png");
        this.heartIcon = loader.load_image("heart-icon.png");
        this.selectIcon = loader.load_image("select-icon.png");
        this.coinIcon = loader.getSubImage(sprite, 1, 5, 48, 48);

        try{
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/font/mario-font.ttf");
            gameFont = Font.createFont(Font.TRUETYPE_FONT, in);
        }catch (FontFormatException | IOException e){
            gameFont = new Font("Verdana", Font.PLAIN, 12);
            e.printStackTrace();
        }

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        status = game.getStatus();

        if(status == Status.START_SCREEN){
            drawStartScreen(g2);
        }
        else if(status == Status.ABOUT_SCREEN){
            drawAboutPage(g2);
        }
        else if(status == Status.HELP_SCREEN){
            drawHelpPage(g2);
        }
        else if(status == Status.MAP_SELECTION){
            drawMapSelect(g2);
        }
        else if(status == Status.RUNNING) {
            Point camLocation = game.getCameraLocation();
            g2.translate(-camLocation.x, -camLocation.y);
            game.getGameHandler().drawMap(g2);
            g2.translate(camLocation.x, camLocation.y);
        }
        else if(status == Status.MISSION_PASSED){
            drawVictoryScreen(g2);
        }
    }

    private void drawStartScreen(Graphics2D g2){
        int row = game.getSelectMenu().getLineNumber();
        g2.drawImage(startScreenImage, 0, 0, null);
        g2.drawImage(selectIcon, 375, row * 70 + 440, null);
    }

    private void drawAboutPage(Graphics2D g2){
        g2.drawImage(aboutScreenImage, 0, 0, null);
    }

    private void drawHelpPage(Graphics2D g2){
        g2.drawImage(helpScreenImage, 0, 0, null);
    }

    private void drawMapSelect(Graphics2D g2){
        g2.setFont(gameFont.deriveFont(50f));
        g2.setColor(Color.WHITE);

        game.getGameHandler().getMapHandler().draw(g2);

        int row = game.getMapID();
        int y = row * 100 + 300 - selectIcon.getHeight();
        g2.drawImage(selectIcon, 375, y, null);
    }

    private void drawVictoryScreen(Graphics2D g2){
        g2.setFont(gameFont.deriveFont(50f));
        g2.setColor(Color.WHITE);
        String text = "YOU WIN!";
        int textLength = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, (getWidth() - textLength) / 2, getHeight() / 2);
    }
}
