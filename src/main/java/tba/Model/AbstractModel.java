package tba.Model;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;

import tba.Utils.DatabaseHandler;

abstract public class AbstractModel {

    protected int id;

    public void save()
    {
        // Si l'objet n'existe pas encore en BDD
        if (id == 0) {
            // Crée un enregistrement à partir des propriétés de l'objet
            insert();
        // SInon
        } else {
            // Modifie l'enregistrement déjà existant à partir des propriétés de l'objet
            update();
        }
    }

    protected void insert()
    {
        try {
            // Récupère l'ensemble des propriétés avec leur valeur, tel que défini dans la classe enfant
            HashMap<String, String> properties = getProperties();
            // Récupère l'ensemble des noms de colonnes
            String[] columnNames = new String[ properties.size() ];
            columnNames = properties.keySet().toArray(columnNames);
            // Prépare un tableau pour contenir les noms de colonne prêts à être insérés dans la requête SQL
            String[] columnNamesToInsert = new String[ properties.size() ];
            // Ajoute des backticks (`) autour de chaque nom de colonne
            for (int i = 0; i < columnNames.length; i += 1) {
                String columnName = columnNames[i];
                columnNamesToInsert[i] = "`" + columnName + "`";
            }
            // Prépare un tableau rempli de "?" pour faire office de champs variables
            String[] placeholders = new String[ properties.size() ];
            Arrays.fill(placeholders, "?");
            
            // Construit la requête SQL...
            String sql =
                // ...avec la clause INSERT INTO et le nom de la table...
                "INSERT INTO `" + getTableName() + "` ("
                // ...la liste des colonnes à insérer, séparées par une virgule...
                + String.join(", ", columnNamesToInsert) +
                // ...la liste des champs variables, séparées par une virgule
                ") VALUES (" + String.join(", ", placeholders) + ");" ;
            
            // Crée la requête SQL à partir de le chaîne constituée précédemment
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Remplit les champs variables avec les différentes valeurs des propriétés
            for (int i = 0; i < properties.size(); i += 1) {
                statement.setString(i + 1, properties.get( columnNames[i] ) );
            }
            // Exécute la requête SQL
            statement.executeUpdate();
            // Récupère l'ID généré par la BDD et l'associe à l'objet
            ResultSet set = statement.getGeneratedKeys();
            if (set.first()) {
                id = set.getInt(1);
                return;
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    protected void update()
    {
        try {
            // Récupère l'ensemble des propriétés avec leur valeur, tel que défini dans la classe enfant
            HashMap<String, String> properties = getProperties();
            // Récupère l'ensemble des noms de colonnes
            String[] columnNames = new String[ properties.size() ];
            columnNames = properties.keySet().toArray(columnNames);
            // Construit la liste des assignations à effectuer
            String[] assignments = new String[ properties.size() ];
            for (int i = 0; i < properties.size(); i += 1) {
                assignments[i] = "`" + columnNames[i] + "` = ?";
            }

            // Construit la requête SQL...
            String sql =
                // ...avec la clause UPDATE et le nom de la table...
                "UPDATE `" + getTableName() + "` SET " +
                // ...la liste des assignations à effectuer, séparées par une virgule...
                String.join(", ", assignments) +
                // ...la clause WHERE permettant de cibler uniquement l'enregistrement désiré
                " WHERE `id` = ?;";
            
            // Crée la requête SQL à partir de le chaîne constituée précédemment
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Remplit les champs variables avec les différentes valeurs des propriétés
            for (int i = 0; i < properties.size(); i += 1) {
                statement.setString(i + 1, properties.get( columnNames[i] ) );
            }
            statement.setInt(properties.size() + 1, id);
            // Exécute la requête SQL
            statement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public void delete()
    {
        try {
            PreparedStatement statement = DatabaseHandler.getInstance().getConnection().prepareStatement("DELETE FROM `" + getTableName() + "` WHERE `id` = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            id = 0;
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    abstract protected String getTableName();

    abstract protected HashMap<String, String> getProperties();

}
