package tba;

import java.util.List;

import tba.Game.Game;
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

        Game game = new Game();

        while (game.isRunning()) {
            game.update();
        }
    }

}
