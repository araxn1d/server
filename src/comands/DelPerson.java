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
public class DelPerson implements ICommand{
        
    private Person toDel;
    
    public DelPerson(Person p){
        toDel=p;
    }
    
    public Person getDelPerson(){
        return toDel;
    }
    
    @Override
    public int getType() {
        return Command.DEL_PERSON;
    }
    
}
