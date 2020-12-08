package tba.Game.GameMode;

import java.time.LocalDateTime;
import java.util.Date;

import tba.Game.Game;
import tba.Game.GameState;

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
        GameState state = game.getState();
        state.setName(userInput);
        state.setCreatedAt( LocalDateTime.now() );
        game.getState().save(userInput + ".dat");
        game.setMode( new NavigationMode(game) );
    }

}
