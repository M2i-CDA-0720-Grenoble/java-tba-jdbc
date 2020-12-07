package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Entity.Direction;
import tba.Entity.Room;
import tba.Entity.RoomTransition;
import tba.Repository.DirectionRepository;
import tba.Repository.RoomRepository;
import tba.Repository.RoomTransitionRepository;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public class RoomTransitionEditMode extends EditorMode {
    
    private Room fromRoom;

    public RoomTransitionEditMode(Editor editor, Room room)
    {
        super(editor);

        fromRoom = room;
    }

    @Override
    public void display() {
        RoomTransitionRepository repository = new RoomTransitionRepository();

        Console.colorPrint("Edit transitions from '" + fromRoom.getName() + "'\n", ConsoleColor.MAGENTA);
        for (RoomTransition transition: repository.findAllFromRoom( fromRoom )) {
            Console.printChoice(transition.getId(), transition.getDirection().getName() + "> " + transition.getToRoom().getName() );
        }
        Console.printChoice(0, "Add new transition...");
    }

    @Override
    public void interpret(int choice) {
        RoomTransitionRepository repository = new RoomTransitionRepository();

        RoomTransition transition;
        if (choice == 0) {
            transition = new RoomTransition();
            transition.setFromRoom(fromRoom);
        } else {
            transition = repository.findById(choice);

            Console.warn("Do you want to delete this transition? (type YES to delete)");
            String input = Console.input().toUpperCase();
            if ("YES".equals(input)) {
                repository.delete(transition);
                return;
            }
        }

        Direction currentDirection = transition.getDirection();
        String prompt = "Select direction";
        if (currentDirection != null) {
            prompt += " [" + currentDirection.getName() + "]\n";
        }
        Console.colorPrint(prompt, ConsoleColor.GREEN_BRIGHT);

        DirectionRepository directionRepository = new DirectionRepository();

        for (Direction direction: directionRepository.findAll()) {
            Console.printChoice(direction.getId(), direction.getName() );
        }
        String input = Console.input();

        int directionId;
        try {
            directionId = Integer.parseInt(input);
        }
        catch (NumberFormatException exception) {
            Console.warn("You must input a number!");
            return;
        }

        transition.setDirection( directionRepository.findById(directionId) );


        Room currentToRoom = transition.getToRoom();
        prompt = "Select landing room";
        if (currentToRoom != null) {
            prompt += " [" + currentToRoom.getName() + "]\n";
        }

        RoomRepository roomRepository = new RoomRepository();

        Console.colorPrint(prompt, ConsoleColor.GREEN_BRIGHT);
        for (Room room: roomRepository.findAll()) {
            Console.printChoice(room.getId(), room.getName() );
        }
        input = Console.input();

        int toRoomId;
        try {
            toRoomId = Integer.parseInt(input);
        }
        catch (NumberFormatException exception) {
            Console.warn("You must input a number!");
            return;
        }

        transition.setToRoom( roomRepository.findById(toRoomId) );

        repository.save(transition);
    }
    
}
