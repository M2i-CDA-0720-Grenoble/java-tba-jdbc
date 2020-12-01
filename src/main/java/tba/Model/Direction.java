package tba.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import tba.Utils.DatabaseHandler;

public class Direction {
    
    private int id;
    private String name;
    private String command;

    public Direction(int id, String name, String command)
    {
        this.id = id;
        this.name = name;
        this.command = command;
    }

    public static List<Direction> findAll() throws SQLException
    {
        List<Direction> directions = new ArrayList<>();
        // Envoie une requête en base de données et récupère les résultats
        ResultSet set = DatabaseHandler.query("SELECT * FROM `direction`");
        // Tant qu'il reste des résultats non traités, prend le résultat suivant...
        while (set.next()) {
            // ... et crée un objet à partir des colonnes présentes dans ce résultat
            Direction direction = new Direction(
                set.getInt("id"),
                set.getString("name"),
                set.getString("command")
            );
            // Ajoute l'objet à la liste
            directions.add(direction);
        }
        // Renvoie la liste
        return directions;
    }

    public static Direction findById(int id) throws SQLException
    {
        // Envoie une requête en base de données
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();
        PreparedStatement statement = dbHandler.getConnection().prepareStatement("SELECT * FROM `direction` WHERE `id` = ?"
            // Rajouter ces deux lignes si on rencontre une erreur de type "Operation not allowed for a result set of type ResultSet.TYPE_FORWARD_ONLY"
            // ,ResultSet.TYPE_SCROLL_SENSITIVE
            // ,ResultSet.CONCUR_UPDATABLE
        );
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();

        // Comme on sait que la requête peut uniquement renvoyer un seul résultat (s'il existe),
        // ou aucun (s'il n'existe pas), cherche le premier résultat de la requête...
        if (set.first()) {
            // ...et renvoie un nouvel objet à partir de ses données
            return new Direction(
                set.getInt("id"),
                set.getString("name"),
                set.getString("command")
            );
        // Si la requête ne renvoie aucun résultat, renvoie null
        } else {
            return null;
        }
    }

    @Override
    public String toString()
    {
        return "[Direction #" + id + "] { name: " + name + ", command: " + command + " }";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

}