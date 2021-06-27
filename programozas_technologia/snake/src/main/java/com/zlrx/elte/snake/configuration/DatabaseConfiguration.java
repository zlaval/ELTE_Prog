package com.zlrx.elte.snake.configuration;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConfiguration {

    private Connection connection;

    private static final DatabaseConfiguration INSTANCE = new DatabaseConfiguration();

    public static DatabaseConfiguration getINSTANCE() {
        return INSTANCE;
    }

    public void startDatabase() {
        try {
            Server.createTcpServer("-tcpPort", "9092", "-ifNotExists", "-tcpPassword", "pw").start();
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/snake", "sa", "");
            connection.setAutoCommit(true);
            createTable();
            System.out.println("Database started");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        var sql = """
                create table if not exists score(
                    id int not null PRIMARY KEY auto_increment,
                    player_name varchar(50) not null,
                    score int not null
                )
                """;
        try (var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void stopDatabase() {
        try {
            connection.close();
            Server.shutdownTcpServer("tcp://localhost:9092", "pw", false, true);
            System.out.println("Database stopped");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
