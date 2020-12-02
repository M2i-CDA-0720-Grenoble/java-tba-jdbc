package tba.Model;

import java.sql.*;
import java.util.HashMap;
import java.util.List;


public class Direction extends AbstractModel<Direction> {
    
    private String name;
    private String command;

    public Direction()
    {
        id = 0;
        name = "";
        command = "";
    }

    public Direction(int id, String name, String command)
    {
        this.id = id;
        this.name = name;
        this.command = command;
    }

    protected String getTableName()
    {
        return "direction";
    }

    protected HashMap<String, String> getProperties()
    {
        return new HashMap<String, String>() {{
            put("name", name);
            put("command", command);
        }};
    }

    protected Direction instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new Direction(
            set.getInt("id"),
            set.getString("name"),
            set.getString("command")
        );
    }

    public static List<Direction> findAll()
    {
        Direction direction = new Direction();
        return direction.findAllGeneric();
    }

    public static Direction findById(int id)
    {
        Direction direction = new Direction();
        return direction.findByIdGeneric(id);
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
