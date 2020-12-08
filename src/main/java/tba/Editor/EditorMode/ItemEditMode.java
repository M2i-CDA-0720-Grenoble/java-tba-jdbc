package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Entity.Item;
import tba.Repository.ItemRepository;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public final class ItemEditMode extends EditorMode {

    public ItemEditMode(Editor editor)
    {
        super(editor);
    }
    
    public void display()
    {
        ItemRepository repository = new ItemRepository();

        for (Item item: repository.findAll()) {
            Console.printChoice(item.getId(), item.getName());
        }
        Console.printChoice(0, "New item...");
    }

    public void interpret(int choice)
    {
        ItemRepository repository = new ItemRepository();

        Item item;
        if (choice == 0) {
            item = new Item();
        } else {
            item = repository.findById(choice);
            if (item == null) {
                Console.warn("Invalid choice!");
                return;
            }
            Console.warn("Do you want to delete '" + item.getName() + "'? (type YES to delete)");
            String userInput = Console.input().toUpperCase();
            if ("YES".equals(userInput)) {
                repository.delete(item);
                return;
            }
        }

        Console.colorPrint("Item name? [" + item.getName() + "]", ConsoleColor.GREEN_BRIGHT);
        String newName = Console.input();
        if (!"".equals(newName)) {
            item.setName(newName);        
        }

        Console.colorPrint("Item description? [" + item.getDescription() + "]", ConsoleColor.GREEN_BRIGHT);
        String newDescription = Console.input();
        if (!"".equals(newDescription)) {
            item.setDescription(newDescription);        
        }

        editor.setMode( new ItemSelectRoomMode( editor, item ) );
    }

}
