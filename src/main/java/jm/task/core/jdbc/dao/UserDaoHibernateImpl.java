package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();
    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();

            session.createNativeQuery("CREATE TABLE IF NOT EXISTS test.users" +
                    " (id mediumint not null auto_increment, name VARCHAR(50), lastname VARCHAR(50), " +
                    "age tinyint, PRIMARY KEY (id))").executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();

            session.createNativeQuery("DROP TABLE IF EXISTS test.users").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName,age));
            transaction.commit();
            System.out.println("User " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();

            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("User удален из бд");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
        criteriaQuery.from(User.class);

        List<User> userList = session.createQuery(criteriaQuery).getResultList();
        try (session) {
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
        }
        System.out.println("Таблица сохранена в виде объектов");
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();

            session.createNativeQuery("TRUNCATE TABLE test.users;").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
