/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp.person;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

/**
 *
 * @author Жека
 */
   public class HibernateUtil {
  private static final SessionFactory sessionFactory;
    static {
      try {
        sessionFactory = new Configuration().configure().buildSessionFactory();
      } catch (Throwable ex) {
        System.err.println("Initial SessionFactory creation failed." + ex);
        throw new ExceptionInInitializerError(ex);
      }
    }

    public static SessionFactory getSessionFactory() {
      return sessionFactory;
    }
}

