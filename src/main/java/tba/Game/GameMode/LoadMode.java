package tba.Game.GameMode;

import java.io.File;
import java.time.format.DateTimeFormatter;

import tba.Game.Game;
import tba.Game.GameState;

public class LoadMode extends GameMode {
    
    public LoadMode(Game game)
    {
        super(game);
    }

    public void display()
    {
        File[] files = getSavegames();
        for (int i = 0; i < files.length; i++) {
            GameState state = GameState.load( files[i].getName() );
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 

            System.out.println("[" + i + "] \"" + state.getName() + "\"  saved on " + dtf.format(state.getCreatedAt()) );
        }
    }

    protected File[] getSavegames()
    {
        return new File( GameState.folderName ).listFiles();
    }

    public void interpret(String userInput)
    {
        File[] files = getSavegames();

        try {
            int choice = Integer.parseInt(userInput);

            if (choice >= 0 && choice < files.length) {
                game.setState( GameState.load( files[choice].getName() ) );
                game.setMode( new NavigationMode( game ) );
            } else {
                System.out.println("Invalid choice!");
            }
        }
        catch(NumberFormatException exception) {
            System.out.println("You must input a number!");
            return;
        }
    }

}
