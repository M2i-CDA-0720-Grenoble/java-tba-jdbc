package tba.Utils;

import java.io.*;
import java.sql.*;

import org.apache.ibatis.jdbc.ScriptRunner;

public class DatabaseHandler
{

    private static DatabaseHandler instance;

    private Connection connection;
    
    private DatabaseHandler() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/java_tba", "root", "root");
    }

    public static DatabaseHandler getInstance() throws SQLException
    {
        if (instance == null) {
            instance = new DatabaseHandler();
        }

        return instance;
    }

    public static boolean execute(String sql) throws SQLException
    {
        Statement statement = getInstance().connection.createStatement();
        return statement.execute(sql);
    }

    public static void runScript(String filename) throws SQLException, FileNotFoundException
    {
        ScriptRunner scriptRunner = new ScriptRunner(getInstance().connection);
        Reader reader = new BufferedReader( new FileReader(filename) );
        scriptRunner.runScript(reader);
    }

}
