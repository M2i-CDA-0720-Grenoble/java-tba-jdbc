package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Model.Direction;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public class DirectionEditMode extends EditorMode {
    
    public DirectionEditMode(Editor editor)
    {
        super(editor);
    }
    
    @Override
    public void display() {
        Console.colorPrint("Edit directions\n", ConsoleColor.MAGENTA);
        for (Direction direction: Direction.findAll()) {
            Console.printChoice(direction.getId(), direction.getName());
        }
        Console.printChoice(0, "Add new direction...");
    }

    @Override
    public void interpret(int choice) {

    }
}
