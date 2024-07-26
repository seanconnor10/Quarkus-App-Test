package org.acme.dataaccess;

import org.acme.model.GameMap;
import org.postgresql.jdbc.PgConnection;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Properties;

public class PostgresMapDao {
    private String url = "";
    private String un = "";
    private String pw = "";

    public PostgresMapDao() throws MissingResourceException {
        try (InputStream propFile = new FileInputStream("./src/main/resources/application.properties")) {
            Properties props = new Properties();
            props.load(propFile);
            url = props.getProperty("quarkus.datasource.jdbc.url");
            un = props.getProperty("quarkus.datasource.username");
            pw = props.getProperty("quarkus.datasource.password");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
    }
    public String getURL() {
        return url;
    }
    public List<GameMap> getMap() {
        List<GameMap> output = new ArrayList<>();

        try (
            Connection connection = DriverManager.getConnection(url, un, pw);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM maps;");
            ResultSet results = statement.executeQuery();
        ) {

            while (results.next()) {
                GameMap mapThisRow = new GameMap();
                mapThisRow.setId(results.getInt("map_id"));
                mapThisRow.setName(results.getString("map_name"));
                mapThisRow.setAuthor(results.getString("author"));
                output.add(mapThisRow);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return output;
    }

}
