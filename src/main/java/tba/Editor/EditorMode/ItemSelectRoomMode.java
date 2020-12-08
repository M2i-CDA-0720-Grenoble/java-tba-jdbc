package tba.Editor.EditorMode;

import tba.Editor.Editor;
import tba.Entity.Item;
import tba.Entity.Room;
import tba.Repository.ItemRepository;
import tba.Repository.RoomRepository;
import tba.Utils.Console;
import tba.Utils.ConsoleColor;

public class ItemSelectRoomMode extends EditorMode {

    protected Item item;
    
    public ItemSelectRoomMode(Editor editor, Item item)
    {
        super(editor);

        this.item = item;
    }

    public void display()
    {
        RoomRepository repository = new RoomRepository();

        Console.colorPrint("What room should the item sit in?", ConsoleColor.GREEN_BRIGHT);

        for (Room room: repository.findAll()) {
            Console.printChoice(room.getId(), room.getName());
        }
    }

    public void interpret(int choice)
    {
        RoomRepository repository = new RoomRepository();

        Room room = repository.findById(choice);
        if (room == null) {
            Console.warn("Invalid choice!");
            return;
        }

        item.setRoom(room);

        ItemRepository itemRepository = new ItemRepository();

        itemRepository.save(item);

        editor.setMode( new ItemEditMode( editor) );
    }

}
