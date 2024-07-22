package java14.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
        public static Connection getConnection() {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                        "postgres",
                        "1234"

                );
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return connection;
        }
}
