/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package threetierapp.person;

/**
 *
 * @author Жека
 */
public class PersonFactory {
    private static iPersonDAO persI=null;
    private static PersonFactory instance=null;
    
    public static synchronized PersonFactory getInstance(){

    if (instance == null){

      instance = new PersonFactory();

    }

    return instance;

  }
    public iPersonDAO getIPers(){
         if (persI == null){

      persI = new PersonDAO();

    }

    return persI;
    }
}
