package tba.Game;

import java.io.*;
import java.util.Date;

import tba.Entity.Room;
import tba.Repository.RoomRepository;
import tba.Utils.ConsoleColor;

public class GameState implements Serializable {

    public static final long serialVersionUID = 1L;
    private static final String folderName = "savegame";
    
    private String name;
    private Date createdAt;

    private int currentRoomId;

    public GameState(int startRoomId)
    {
        currentRoomId = startRoomId;
    }

    public static GameState load(String filename)
    {
        try {
            FileInputStream file = new FileInputStream(folderName + "\\" + filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(file);
            GameState state = (GameState)objectInputStream.readObject();
            objectInputStream.close();
            return state;
        }
        catch (FileNotFoundException exception) {
            System.out.println(ConsoleColor.RED + "Save file not found." + ConsoleColor.RESET);
            return null;
        }
        catch (IOException exception) {
            System.out.println(ConsoleColor.RED + "Error while reading save file." + ConsoleColor.RESET);
            exception.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException exception) {
            System.out.println(ConsoleColor.RED + "GameState class deleted since save." + ConsoleColor.RESET);
            return null;
        }
    }

    public void save(String filename)
    {
        try {
            FileOutputStream file = new FileOutputStream(folderName + "\\" + filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(file);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(ConsoleColor.RED + "Save file not found." + ConsoleColor.RESET);
            return;
        }
        catch (IOException exception) {
            System.out.println(ConsoleColor.RED + "Error while writing save file." + ConsoleColor.RESET);
            return;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Room getCurrentRoom()
    {
        RoomRepository repository = new RoomRepository();
        return repository.findById(currentRoomId);
    }

    public GameState setCurrentRoom(Room room)
    {
        currentRoomId = room.getId();
        return this;
    }

}
