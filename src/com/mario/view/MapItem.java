package com.mario.view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapItem {

    private BufferedImage background;
    private String name;
    private Point location;
    private Dimension dimension;

    public Point getLocation() {
        return location;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public MapItem(String name, Point location) throws IOException {
        this.location = location;
        this.name = name;

        ImageLoader loader = new ImageLoader();
        this.background = loader.load_image("maps/" + name);

        this.dimension = new Dimension();
    }

    public String getName(){
        return name;
    }
}
