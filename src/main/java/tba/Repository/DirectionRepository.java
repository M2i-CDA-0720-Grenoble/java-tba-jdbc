package tba.Repository;

import java.sql.*;

import tba.Entity.Direction;
import tba.Utils.Repository;

public final class DirectionRepository extends Repository<Direction> {
    
    public String getTableName()
    {
        return "direction";
    }

    public Direction instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new Direction(
            set.getInt("id"),
            set.getString("name"),
            set.getString("command")
        );
    }

}
