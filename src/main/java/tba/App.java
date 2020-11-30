package tba;

import tba.Model.Room;
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

        // Affiche toutes les pièces présentes dans la base de données
        for (Room room: Room.findAll()) {
            System.out.println(room);
        }

        // Affiche uniquement la pièce n°3 de la base de données
        System.out.println( Room.findById(3) );
    }

}
