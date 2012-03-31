/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comands;

import threetierapp.person.Person;

/**
 *
 * @author Жека
 */
public class AddPerson implements ICommand {

    Person curr;

    public AddPerson(Person p) {
        curr = p;
    }

    public Person getPerson() {
        return curr;
    }
    

    @Override
    public int getType() {
        return Command.ADD_PERSON;
    }
}
