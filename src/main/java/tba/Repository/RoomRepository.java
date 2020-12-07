package tba.Repository;

import java.sql.*;

import tba.Entity.Room;
import tba.Utils.Repository;

public final class RoomRepository extends Repository<Room> {

    public String getTableName()
    {
        return "room";
    }

    public Room instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new Room(
            set.getInt("id"),
            set.getString("name"),
            set.getString("description")
        );
    }

}
