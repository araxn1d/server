/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.SQLException;
import java.util.ArrayList;
import threetierapp.person.Person;
import threetierapp.person.iPersonDAO;

/**
 *
 * @author Jeka
 */
public class DAOMock implements iPersonDAO {

    ArrayList<Person> pers = new ArrayList<Person>();

    @Override
    public void updatePerson(ArrayList<Person> persons) throws SQLException {
        if (null != persons) {
            for (Person p : persons) {
                pers.add(p);
            }
        }
    }

    @Override
    public void updatePerson(Person person) throws SQLException {
        if (null != person) {
            pers.add(person);
        } else {
            throw new SQLException();
        }
    }

    @Override
    public Person getPersonById(int ID) throws SQLException {
        Person ans = null;
        if (ID > 0) {
            for (Person p : pers) {
                if (p.getId() == ID) {
                    ans = p;
                }
            }
        }
        if (ans == null) {
            throw new SQLException();
        }
        return ans;
    }

    @Override
    public ArrayList<Person> getPersons() throws SQLException {
        if(null!=pers){
        return pers;}
        else throw new SQLException();
    }

    @Override
    public void delPerson(Person person) throws SQLException {
        if((null!=person) && (person.getId()>0)){
            pers.remove(person);
        }
        else throw new SQLException();
    }
}
