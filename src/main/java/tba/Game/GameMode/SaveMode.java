package tba.Game.GameMode;

import tba.Game.Game;

public class SaveMode extends GameMode {
    
    public SaveMode(Game game)
    {
        super(game);
    }

    public void display()
    {
        System.out.println("Choose a name for your savegame");
    }

    public void interpret(String userInput)
    {
        game.getState().save(userInput + ".dat");
        game.setMode( new NavigationMode(game) );
    }

}
