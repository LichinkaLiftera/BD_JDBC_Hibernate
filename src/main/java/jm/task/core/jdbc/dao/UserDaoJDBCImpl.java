package jm.task.core.jdbc.dao;

import com.sun.jdi.connect.Connector;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    @Override
    public void createUsersTable() {
        final String newTable = "CREATE TABLE Users" +
                "(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,Name VARCHAR(30) NOT NULL, " +
                "Second_Name VARCHAR(30) NOT NULL, Age INT)";
        try (Statement statement = Util.getConnect().createStatement()) {
            statement.executeUpdate(newTable);

            System.out.println("Create done");

        } catch (Exception e) {
            System.out.println("Fiasko -> ");
        }

    }

    @Override
    public void dropUsersTable() {
        final String deleteTable = "DROP TABLE Users";
        try(Statement statement = Util.getConnect().createStatement()){
            statement.execute(deleteTable);

            System.out.println("Delete done");

        }catch (SQLException e){
            System.out.println("Fiasko -> " + e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String newUser = "INSERT INTO Users(Name,Second_Name,Age) VALUES (?,?,?)";
        try(PreparedStatement preparedStatement = Util.getConnect().prepareStatement(newUser);){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.execute();

            System.out.println("Add done");

        }catch (Exception e){
            System.out.println("Fiasko -> " + e);
        }

    }

    @Override
    public void removeUserById(long id) {
        final String delete = "DELETE FROM Users WHERE ID = ?;";
        try(PreparedStatement preparedStatement = Util.getConnect().prepareStatement(delete)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Remove done");

        }catch(SQLException e){
            System.out.println("Fiasko -> " + e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        final String allUsers = "SELECT * FROM Users;";
        List<User> users = new ArrayList<>();
        try(Statement statement = Util.getConnect().createStatement()){
            ResultSet usersSet = statement.executeQuery(allUsers);
            while(usersSet.next()){
                User user = new User(usersSet.getString(2),
                        usersSet.getString(3), usersSet.getByte(4));
                users.add(user);
            }
            System.out.println("User List created success");

        } catch (SQLException e) {
            System.out.println("Fiasko getAll-> " + e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        final String cleanTable = "DELETE FROM Users";
        try(Statement statement = Util.getConnect().createStatement()){
            statement.execute(cleanTable);

            System.out.println("Clean done");

        } catch (Exception e) {
            System.out.println("Fiasko clear-> " + e);
        }
    }
}
