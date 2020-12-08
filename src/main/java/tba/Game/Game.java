package tba.Game;

import java.sql.SQLException;
import java.util.Scanner;

import tba.Entity.Direction;
import tba.Entity.Room;
import tba.Entity.RoomTransition;
import tba.Repository.DirectionRepository;
import tba.Repository.RoomTransitionRepository;
import tba.Utils.ConsoleColor;

public class Game {

    private GameState state;
    private boolean isRunning;
    private Scanner scanner;

    public Game()
    {
        state = new GameState(1);

        scanner = new Scanner(System.in);

        isRunning = true;
    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void terminate()
    {
        isRunning = false;
    }

    public void update() throws SQLException
    {
        // Décrit la pièce dans laquelle se trouve le joueur actuellement
        Room currentRoom = state.getCurrentRoom();
        System.out.println(ConsoleColor.CYAN + "You are in the " + currentRoom.getName() + ".\n" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + currentRoom.getDescription() + ConsoleColor.RESET);

        RoomTransitionRepository transitionRepository = new RoomTransitionRepository();

        for (RoomTransition transition: transitionRepository.findAllFromRoom(currentRoom)) {
            System.out.println(ConsoleColor.GREEN + transition.getDirection().getName() + " is the " + transition.getToRoom().getName() + "." + ConsoleColor.RESET);
        }

        // Demande à l'utilisateur de saisir une commande
        System.out.println("");
        System.out.print("> ");
        String userInput = scanner.nextLine().trim().toLowerCase();

        if ("load".equals(userInput)) {
            state = GameState.load("test.dat");
            return;
        }
        if ("save".equals(userInput)) {
            state.save("test.dat");
            return;
        }

        DirectionRepository directionRepository = new DirectionRepository();

        // Cherche si la saisie de l'utilisateur correspond à une commande de direction
        for (Direction direction: directionRepository.findAll()) {
            // Si la direction saisie par l'utilisateur existe
            if (userInput.equals(direction.getCommand())) {

                // Récupère la transition qui part de la pièce actuelle dans la direction demandée
                RoomTransition transition = transitionRepository.findByFromRoomAndDirection(currentRoom, direction);

                // S'il n'existe pas de transition partant de la pièce actuelle dans la direction
                if (transition == null) {
                    System.out.println(ConsoleColor.YELLOW + "You cannot go into that direction!" + ConsoleColor.RESET);
                // Sinon, modifie la pièce actuelle
                } else {
                    state.setCurrentRoom( transition.getToRoom() );
                }

                return;

            }
        }
    }
}
