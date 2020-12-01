package tba;

import java.util.List;

import tba.Model.Direction;
import tba.Model.Room;
import tba.Model.RoomTransition;
import tba.Utils.DatabaseHandler;

/**
 * Hello world!
 */
public final class App {

    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) throws Exception {
        DatabaseHandler.runScript("schema.sql");
        DatabaseHandler.runScript("data.sql");

        // Récupèré une pièce
        Room room = Room.findById(1);
        System.out.println(room);
        System.out.println("");

        // Récupère une direction
        Direction direction = Direction.findById(4);
        System.out.println(direction);
        System.out.println("");

        // Récupère la transition qui part de cette pièce, et qui va dans cette direction,
        // et affiche la pièce vers laquelle pointe cette transition
        RoomTransition transition = RoomTransition.findByFromRoomAndDirection(room, direction);
        System.out.println(transition.getToRoom());
        System.out.println("");
    }

}
