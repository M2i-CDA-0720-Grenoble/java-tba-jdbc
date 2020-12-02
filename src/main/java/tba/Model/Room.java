package tba.Model;

import java.sql.*;
import java.util.HashMap;
import java.util.List;


public class Room extends AbstractModel<Room>
{

    private String name;
    private String description;

    public Room()
    {
        id = 0;
        name = "";
        description = "";
    }

    public Room(int id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    protected String getTableName()
    {
        return "room";
    }

    protected HashMap<String, String> getProperties()
    {
        return new HashMap<String, String>() {{
            put("name", name);
            put("description", description);
        }};
    }

    protected Room instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new Room(
            set.getInt("id"),
            set.getString("name"),
            set.getString("description")
        );
    }

    public static List<Room> findAll()
    {
        Room room = new Room();
        return room.findAllGeneric();
    }

    public static Room findById(int id)
    {
        Room room = new Room();
        return room.findByIdGeneric(id);
    }

    @Override
    public String toString()
    {
        return "[Room #" + id + "] { name: " + name + ", description: " + description +" }";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
