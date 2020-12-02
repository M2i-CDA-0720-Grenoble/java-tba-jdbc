package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Model.Direction;
import tba.Model.Room;
import tba.Model.RoomTransition;
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
        Console.colorPrint("Edit transitions from '" + fromRoom.getName() + "'\n", ConsoleColor.MAGENTA);
        for (RoomTransition transition: RoomTransition.findAllFromRoom( fromRoom )) {
            Console.printChoice(transition.getId(), transition.getDirection().getName() + "> " + transition.getToRoom().getName() );
        }
        Console.printChoice(0, "Add new transition...");
    }

    @Override
    public void interpret(int choice) {
        RoomTransition transition;
        if (choice == 0) {
            transition = new RoomTransition();
            transition.setFromRoom(fromRoom);
        } else {
            transition = RoomTransition.findById(choice);

            Console.warn("Do you want to delete this transition? (type YES to delete)");
            String input = Console.input().toUpperCase();
            if ("YES".equals(input)) {
                transition.delete();
                return;
            }
        }

        Direction currentDirection = transition.getDirection();
        String prompt = "Select direction";
        if (currentDirection != null) {
            prompt += " [" + currentDirection.getName() + "]\n";
        }
        Console.colorPrint(prompt, ConsoleColor.GREEN_BRIGHT);
        for (Direction direction: Direction.findAll()) {
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

        transition.setDirection( Direction.findById(directionId) );


        Room currentToRoom = transition.getToRoom();
        prompt = "Select landing room";
        if (currentToRoom != null) {
            prompt += " [" + currentToRoom.getName() + "]\n";
        }

        Console.colorPrint(prompt, ConsoleColor.GREEN_BRIGHT);
        for (Room room: Room.findAll()) {
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

        transition.setToRoom( Room.findById(toRoomId) );

        transition.save();
    }
    
}
