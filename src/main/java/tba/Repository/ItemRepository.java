package tba.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import tba.Entity.Item;
import tba.Utils.Repository;

public class ItemRepository extends Repository<Item> {
    
    public String getTableName()
    {
        return "item";
    }

    public Item instantiateFromResultSet(ResultSet set) throws SQLException
    {
        return new Item(
            set.getInt("id"),
            set.getString("name"),
            set.getString("description"),
            set.getInt("room_id")
        );
    }

}
