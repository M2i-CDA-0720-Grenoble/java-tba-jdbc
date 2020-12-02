package tba.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class RoomTransition extends AbstractModel<RoomTransition> {
    
    private int fromRoomId;
    private int toRoomId;
    private int directionId;

    public RoomTransition()
    {
        id = 0;
        fromRoomId = 0;
        toRoomId = 0;
        directionId = 0;
    }

    public RoomTransition(int id, int fromRoomId, int toRoomId, int directionId) {
        this.id = id;
        this.fromRoomId = fromRoomId;
        this.toRoomId = toRoomId;
        this.directionId = directionId;
    }

    @Override
    protected String getTableName()
    {
        return "room_transition";
    }

    @Override
    protected HashMap<String, String> getProperties() {
        return new HashMap<String, String>() {{
            put("from_room_id", Integer.toString(fromRoomId));
            put("to_room_id", Integer.toString(toRoomId));
            put("direction_id", Integer.toString(directionId));
        }};
    }

    @Override
    protected RoomTransition instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new RoomTransition(
            set.getInt("id"),
            set.getInt("from_room_id"),
            set.getInt("to_room_id"),
            set.getInt("direction_id")
        );
    }

    public static RoomTransition findById(int id)
    {
        RoomTransition transition = new RoomTransition();
        return transition.findByIdGeneric(id);
    }

    public static List<RoomTransition> findAllFromRoom(Room room)
    {
        RoomTransition transition = new RoomTransition();
        return transition.findByCriteria(new HashMap<String, String>() {{
            put("from_room_id", Integer.toString( room.getId() ));
        }});
    }

    public static RoomTransition findByFromRoomAndDirection(Room room, Direction direction)
    {
        RoomTransition transition = new RoomTransition();
        List<RoomTransition> transitions = transition.findByCriteria(new HashMap<String, String>() {{
            put("from_room_id", Integer.toString( room.getId() ));
            put("direction_id", Integer.toString( direction.getId() ));
        }});

        if (transitions.size() == 0) {
            return null;
        } else {
            return transitions.get(0);
        }
    }

    @Override
    public String toString()
    {
        return "[RoomTransition #" + id + "] {\n fromRoom: " + getFromRoom() + ",\n toRoom: " + getToRoom() + ",\n direction: " + getDirection() + "\n}";
    }

    public int getId() {
        return id;
    }

    public Room getFromRoom()
    {
        return Room.findById(fromRoomId);
    }

    public Room getToRoom()
    {
        return Room.findById(toRoomId);
    }

    public RoomTransition setFromRoom(Room room)
    {
        fromRoomId = room.getId();
        return this;
    }

    public RoomTransition setToRoom(Room room)
    {
        toRoomId = room.getId();
        return this;
    }

    public Direction getDirection()
    {
        return Direction.findById(directionId);
    }

    public RoomTransition setDirection(Direction direction)
    {
        directionId = direction.getId();
        return this;
    }

}
