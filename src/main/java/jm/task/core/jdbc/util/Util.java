package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Util {
    private static Connection connection = null;
    private static Util instance = null;

    private Util() {
        try {
            Properties properties = getProperties();
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(
                Paths.get(Objects.requireNonNull(Util.class.getResource("/database.properties")).toURI())))
        {
            properties.load(input);
            return properties;
        }  catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
