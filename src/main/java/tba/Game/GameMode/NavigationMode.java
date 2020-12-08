package tba.Game.GameMode;

import tba.Entity.Direction;
import tba.Entity.Room;
import tba.Entity.RoomTransition;
import tba.Game.Game;
import tba.Repository.DirectionRepository;
import tba.Repository.RoomTransitionRepository;
import tba.Utils.ConsoleColor;

public class NavigationMode extends GameMode {
    
    public NavigationMode(Game game)
    {
        super(game);
    }

    protected Room getCurrentRoom()
    {
        return game.getState().getCurrentRoom();
    }

    public void display()
    {
        // Décrit la pièce dans laquelle se trouve le joueur actuellement
        Room currentRoom = getCurrentRoom();
        System.out.println(ConsoleColor.CYAN + "You are in the " + currentRoom.getName() + ".\n" + ConsoleColor.RESET);
        System.out.println(ConsoleColor.CYAN + currentRoom.getDescription() + ConsoleColor.RESET);

        RoomTransitionRepository transitionRepository = new RoomTransitionRepository();

        for (RoomTransition transition: transitionRepository.findAllFromRoom(currentRoom)) {
            System.out.println(ConsoleColor.GREEN + transition.getDirection().getName() + " is the " + transition.getToRoom().getName() + "." + ConsoleColor.RESET);
        }        
    }

    public void interpret(String userInput)
    {
        DirectionRepository directionRepository = new DirectionRepository();
        RoomTransitionRepository transitionRepository = new RoomTransitionRepository();

        // Cherche si la saisie de l'utilisateur correspond à une commande de direction
        for (Direction direction: directionRepository.findAll()) {
            // Si la direction saisie par l'utilisateur existe
            if (userInput.equals(direction.getCommand())) {

                // Récupère la transition qui part de la pièce actuelle dans la direction demandée
                RoomTransition transition = transitionRepository.findByFromRoomAndDirection(getCurrentRoom(), direction);

                // S'il n'existe pas de transition partant de la pièce actuelle dans la direction
                if (transition == null) {
                    System.out.println(ConsoleColor.YELLOW + "You cannot go into that direction!" + ConsoleColor.RESET);
                // Sinon, modifie la pièce actuelle
                } else {
                    game.getState().setCurrentRoom( transition.getToRoom() );
                }

                return;

            }
        }
    }

}
