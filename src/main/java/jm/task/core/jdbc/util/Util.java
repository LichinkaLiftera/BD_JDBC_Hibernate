package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/testbase";
    private static final String userName = "root";
    private static final String pass = "19930703andrey";

    public static Connection getConnect() {
        try {
            return DriverManager.getConnection(url, userName, pass);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static SessionFactory factory(){
        try {
            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/testbase");
            properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.connection.username", "root");
            properties.setProperty("hibernate.connection.password", "19930703andrey");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("show_sql", "true");
            properties.setProperty("hibernate.current_session_context_class", "thread");

            return new Configuration().addProperties(properties).
                    addAnnotatedClass(User.class).buildSessionFactory();

        }catch (Exception e) {
            System.out.print("Fiasko connect with BD -> ");
            e.getStackTrace();
            System.out.println();
        }
        return null;
    }

}
