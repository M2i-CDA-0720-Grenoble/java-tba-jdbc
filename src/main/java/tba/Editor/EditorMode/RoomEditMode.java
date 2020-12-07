package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Entity.Room;
import tba.Repository.RoomRepository;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public class RoomEditMode extends EditorMode {
    
    public RoomEditMode(Editor editor)
    {
        super(editor);
    }

    @Override
    public void display() {
        RoomRepository repository = new RoomRepository();

        Console.colorPrint("Edit rooms\n", ConsoleColor.MAGENTA);
        for (Room room: repository.findAll()) {
            Console.printChoice(room.getId(), room.getName());
        }
        Console.printChoice(0, "Add new room...");
    }

    @Override
    public void interpret(int choice) {
        RoomRepository repository = new RoomRepository();

        Room room;
        if (choice == 0) {
            room = new Room();
            Console.colorPrint("Editing new room", ConsoleColor.MAGENTA);
        } else {
            room = repository.findById(choice);
            Console.colorPrint("Editing '" + room.getName() + "'", ConsoleColor.MAGENTA);

            Console.warn("Do you want to delete '" + room.getName() + "'? (type YES to delete)");
            String input = Console.input().toUpperCase();
            if ("YES".equals(input)) {
                repository.delete(room);
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

        repository.save(room);

        editor.setMode( new RoomTransitionEditMode(editor, room) );
    }
    
}
