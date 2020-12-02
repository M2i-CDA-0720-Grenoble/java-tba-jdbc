package tba.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import tba.Utils.DatabaseHandler;

abstract public class AbstractModel<T> {

    protected int id;


    protected List<T> findAllGeneric()
    {
        try {
            // Crée une liste prête à accueillir les objets qui vont être créés à partir des données récupérées
            List<T> result = new ArrayList<>();
            // Envoie une requête en base de données et récupère les résultats
            ResultSet set = DatabaseHandler.query("SELECT * FROM `" + getTableName() + "`");
            // Tant qu'il reste des résultats non traités, prend le résultat suivant...
            while (set.next()) {
                // ... et crée un objet à partir des colonnes présentes dans ce résultat
                T object = instantiateFromResultSet(set);
                // Ajoute l'objet à la liste
                result.add(object);
            }
            // Renvoie la liste
            return result;
        }
        catch (SQLException exception) {
            System.out.println(exception);
            System.exit(1);
            return null;
        }
    }


    public T findByIdGeneric(int id)
    {
        try {
            // Envoie une requête en base de données
            DatabaseHandler dbHandler = DatabaseHandler.getInstance();
            PreparedStatement statement = dbHandler.getConnection().prepareStatement("SELECT * FROM `" + getTableName() + "` WHERE `id` = ?"
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
                return instantiateFromResultSet(set);
            // Si la requête ne renvoie aucun résultat, renvoie null
            } else {
                return null;
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(1);
            return null;
        }
    }


    /**
     * Update matching database record based on this object's properties
     */
    public void save()
    {
        // Si l'objet n'existe pas encore en BDD
        if (id == 0) {
            // Crée un enregistrement à partir des propriétés de l'objet
            insert();
        // Sinon
        } else {
            // Modifie l'enregistrement déjà existant à partir des propriétés de l'objet
            update();
        }
    }


    /**
     * Create new record in database based on this object's properties
     * @see AbstractModel#save()
     */
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


    /**
     * Update macthing existing record in database based on this object's properties
     * @see AbstractModel#save()
     */
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


    /**
     * Delete matching record from database
     */
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


    /**
     * Specify table name associated with this entity
     */
    abstract protected String getTableName();

    /**
     * Pack object properties as a collection of { columnName => value }
     */
    abstract protected HashMap<String, String> getProperties();

    abstract protected T instantiateFromResultSet(ResultSet set) throws SQLException;
}
