package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Model.Room;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public class RoomEditMode extends EditorMode {
    
    public RoomEditMode(Editor editor)
    {
        super(editor);
    }

    @Override
    public void display() {
        Console.colorPrint("Edit rooms\n", ConsoleColor.MAGENTA);
        for (Room room: Room.findAll()) {
            Console.printChoice(room.getId(), room.getName());
        }
        Console.printChoice(0, "Add new room...");
    }

    @Override
    public void interpret(int choice) {
        Room room;
        if (choice == 0) {
            room = new Room();
            Console.colorPrint("Editing new room", ConsoleColor.MAGENTA);
        } else {
            room = Room.findById(choice);
            Console.colorPrint("Editing '" + room.getName() + "'", ConsoleColor.MAGENTA);

            Console.warn("Do you want to delete '" + room.getName() + "'? (type YES to delete)");
            String input = Console.input().toUpperCase();
            if ("YES".equals(input)) {
                room.delete();
                return;
            }
        }

        Console.colorPrint("Room name? [" + room.getName() + "]", ConsoleColor.GREEN_BRIGHT);
        String name = Console.input();
        if (!"".equals(name)) {
            room.setName(name);
        }

        Console.colorPrint("Room description? [" + room.getDescription() + "]", ConsoleColor.GREEN_BRIGHT);
        String description = Console.input();
        if (!"".equals(description)) {
            room.setDescription(description);
        }

        room.save();
    }
    
}
