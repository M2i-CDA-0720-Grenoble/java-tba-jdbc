package tba.Game.GameMode;

import tba.Game.Game;

public abstract class GameMode {

    protected Game game;

    public GameMode(Game game)
    {
        this.game = game;
    }
    
    abstract public void display();

    abstract public void interpret(String userInput);

}
