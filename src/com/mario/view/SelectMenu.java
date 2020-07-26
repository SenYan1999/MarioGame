package com.mario.view;

public enum SelectMenu {
    START_GAME(0),
    HELP_PAGE(1),
    ABOUT_PAGE(2);

    private final int lineNumber;
    SelectMenu(int lineNumber){
        this.lineNumber = lineNumber;
    }

    public SelectMenu getSelection(int number){
        if(number == 0)
            return START_GAME;
        else if(number == 1)
            return HELP_PAGE;
        else if(number == 2)
            return ABOUT_PAGE;
        else
            return null;
    }

    public SelectMenu select(boolean toUP){
        int selection;

        if(lineNumber > -1 && lineNumber < 3){
            selection = lineNumber - (toUP ? 1: -1);
            if(selection == -1)
                selection = 2;
            else if(selection == 3)
                selection = 0;
            return getSelection(selection);
        }

        return null;
    }

    public int getLineNumber(){
        return lineNumber;
    }
}
