/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp.person;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Жека
 */
public interface iPersonDAO {

        void updatePerson(ArrayList<Person> persons)throws SQLException;
	void updatePerson(Person person)throws SQLException;
	Person getPersonById(int ID)throws SQLException;
	ArrayList<Person> getPersons()throws SQLException;
	void delPerson(Person person)throws SQLException;
}
