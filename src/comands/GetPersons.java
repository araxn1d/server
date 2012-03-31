/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comands;

import java.util.ArrayList;
import threetierapp.person.Person;

/**
 *
 * @author Жека
 */
public class GetPersons implements ICommand {

    private ArrayList<Person> p;

    public GetPersons() {
    }

    public GetPersons(ArrayList<Person> pe) {
        p = pe;
    }
    
    public ArrayList<Person> persons(){
        return p;
    }

    @Override
    public int getType() {
        return Command.GET_PERSONS;
    }
}
