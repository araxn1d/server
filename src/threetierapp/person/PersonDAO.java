/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp.person;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author Жека
 */
public class PersonDAO implements iPersonDAO {

    @Override
    public void updatePerson(ArrayList<Person> persons) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (Person pers : persons) {
                session.saveOrUpdate(pers);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(person);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    @Override
    public Person getPersonById(int ID) throws SQLException {
        Session session = null;
        Person pers = null;
            session = HibernateUtil.getSessionFactory().openSession();
            pers = (Person) session.load(Person.class, ID);
        return pers;
    }

    @Override
    public ArrayList<Person> getPersons() throws SQLException {
        Session session = null;
        ArrayList persons = new ArrayList<Person>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            persons = (ArrayList) session.createCriteria(Person.class).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return persons;
    }

    @Override
    public void delPerson(Person person) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(person);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
