package tba.Game;

import java.sql.SQLException;
import java.util.Scanner;

import tba.Model.Direction;
import tba.Model.Room;
import tba.Model.RoomTransition;

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
        System.out.println("You are in the " + currentRoom.getName() + ".\n");
        System.out.println(currentRoom.getDescription());

        for (RoomTransition transition: RoomTransition.findAllFromRoom(currentRoom)) {
            System.out.println(transition.getDirection().getName() + " is the " + transition.getToRoom().getName() + ".");
        }

        // Demande à l'utilisateur de saisir une commande
        String userInput = scanner.nextLine().trim().toLowerCase();
        System.out.println(userInput);

        // Cherche si la saisie de l'utilisateur correspond à une commande de direction
        for (Direction direction: Direction.findAll()) {
            // Si la direction saisie par l'utilisateur existe
            if (userInput.equals(direction.getCommand())) {

                // Récupère la transition qui part de la pièce actuelle dans la direction demandée
                RoomTransition transition = RoomTransition.findByFromRoomAndDirection(currentRoom, direction);

                // S'il n'existe pas de transition partant de la pièce actuelle dans la direction
                if (transition == null) {
                    System.out.println("You cannot go into that direction!");
                // Sinon, modifie la pièce actuelle
                } else {
                    state.setCurrentRoom( transition.getToRoom() );
                }

                return;

            }
        }
    }
}
