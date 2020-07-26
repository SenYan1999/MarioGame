package com.mario.view;

import com.mario.main.Game;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class MapHandler {

    private ArrayList<String> maps = new ArrayList<>();
    private MapItem[] mapItems;

    public MapHandler() throws IOException {
        maps.add("Map 1.png");
        maps.add("Map 2.png");

        this.mapItems = createMapItems(maps);
    }

    public int changeSelectedMap(int idx, boolean up){
        if(up){
            if(idx <= 0){
                return mapItems.length - 1;
            }
            else
                return idx - 1;
        }
        else{
            if(idx >= mapItems.length - 1)
                return 0;
            else
                return idx + 1;
        }
    }

    private MapItem[] createMapItems(ArrayList<String> maps) throws IOException {
        if(maps == null){
            return null;
        }

        int defaultGridSize = 100;
        MapItem[] mapItems = new MapItem[maps.size()];
        for(int i = 0; i < mapItems.length; i++){
            mapItems[i] = new MapItem(maps.get(i), new Point(0, (i+1) * defaultGridSize + 200));
        }

        return mapItems;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1280, 720);

        String title = "Select a Map";
        int x_location = (1280 - g.getFontMetrics().stringWidth(title)) / 2;
        g.setColor(Color.YELLOW);
        g.drawString(title, x_location, 150);

        for(MapItem item : mapItems){
            g.setColor(Color.WHITE);
            int width = g.getFontMetrics().stringWidth(item.getName().split("[.]")[0]);
            int height = g.getFontMetrics().getHeight();
            item.setDimension(new Dimension(width, height));
            item.setLocation(new Point((1280 - width) / 2, item.getLocation().y));
            g.drawString(item.getName().split("[.]")[0], item.getLocation().x, item.getLocation().y);
        }
    }

    public String ChangeMap(int idx){
        if(idx < mapItems.length && idx > -1){
            return mapItems[idx].getName();
        }
        return null;
    }

}

