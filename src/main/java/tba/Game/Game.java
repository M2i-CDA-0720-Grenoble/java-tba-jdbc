package tba.Game;

import java.util.Scanner;

import tba.Game.GameMode.*;

public class Game {

    private GameState state;
    private boolean isRunning;
    private Scanner scanner;
    private GameMode mode;

    public Game()
    {
        state = new GameState(1);

        scanner = new Scanner(System.in);

        isRunning = true;

        mode = new NavigationMode( this );
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void terminate()
    {
        isRunning = false;
    }

    public void update()
    {
        mode.display();

        // Demande à l'utilisateur de saisir une commande
        System.out.println("");
        System.out.print("> ");
        String userInput = scanner.nextLine().trim().toLowerCase();

        if ("load".equals(userInput)) {
            setMode( new LoadMode( this ) );
            return;
        }
        if ("save".equals(userInput)) {
            setMode( new SaveMode( this ) );
            return;
        }

        mode.interpret(userInput);
    }

    public GameState getState()
    {
        return state;
    }

    public Game setMode(GameMode mode)
    {
        this.mode = mode;

        return this;
    }

    public Game setState(GameState state)
    {
        this.state = state;

        return this;
    }
}
