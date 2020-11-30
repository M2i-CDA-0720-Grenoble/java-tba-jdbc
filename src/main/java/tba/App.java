package tba;

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

        System.out.println("Hello World!");
    }

}
