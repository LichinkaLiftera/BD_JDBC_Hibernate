package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    @Override
    public void createUsersTable() {
        final String newTable = "CREATE TABLE Users" +
                "(ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,Name VARCHAR(30) NOT NULL, " +
                "Second_Name VARCHAR(30) NOT NULL, Age INT)";

        try (Session session = new Util().factory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(newTable).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();

            System.out.println("Create done");

        } catch (Exception e) {
            System.out.print("Fiasko Create -> ");
            e.printStackTrace();
            System.out.println();
        }

    }

    @Override
    public void dropUsersTable() {
        final String deleteTable = "DROP TABLE Users";

        try (Session session = new Util().factory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(deleteTable).executeUpdate();
            session.getTransaction().commit();

            System.out.println("Drop done");

        } catch (Exception e) {
            System.out.print("Fiasko Drop -> ");
            e.printStackTrace();
            System.out.println();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = new Util().factory().getCurrentSession()) {
            User user = new User(name, lastName, (byte) age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

            System.out.println("User added");

        } catch (Exception e) {
            System.out.print("Fiasko addUser -> ");
            e.printStackTrace();
            System.out.println();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = new Util().factory().getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();

            System.out.println("IdRemove done");

        } catch (Exception e) {
            System.out.print("Fiasko removeId -> ");
            e.printStackTrace();
            System.out.println();
        }
    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.factory().getCurrentSession()) {
            session.beginTransaction();
            List<User> users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();

            System.out.println("GetAll done");

            return users;

        } catch (Exception e) {
            System.out.print("Fiasko getAll -> ");
            e.printStackTrace();
            System.out.println();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.factory().getCurrentSession()){
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();

            System.out.println("Delete done");

        }catch (Exception e){
            System.out.print("Fiasko cleanTable -> ");
            e.printStackTrace();
            System.out.println();
        }

    }
}
