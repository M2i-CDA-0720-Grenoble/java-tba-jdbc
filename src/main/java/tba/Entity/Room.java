package tba.Entity;

import java.util.HashMap;


public class Room extends Entity
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

    public HashMap<String, String> getProperties()
    {
        return new HashMap<String, String>() {{
            put("name", name);
            put("description", description);
        }};
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
