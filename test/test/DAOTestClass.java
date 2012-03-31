/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.sql.SQLException;
import org.junit.*;
import static org.junit.Assert.*;
import threetierapp.person.PersonDAO;
import static org.mockito.Mockito.*;
import sun.awt.windows.ThemeReader;
import threetierapp.ServerThread;
import threetierapp.person.Person;
import threetierapp.person.PersonFactory;
import threetierapp.person.iPersonDAO;

/**
 *
 * @author Jeka
 */
public class DAOTestClass {
    
    
    public DAOTestClass() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test 
    public void  personFactoryTest(){
            PersonDAO mock=new PersonDAO();
            PersonFactory fact=mock(PersonFactory.class);
            when(fact.getIPers()).thenReturn(mock);
            assertEquals(fact.getIPers(),mock);
    }
  
    @Test (expected=SQLException.class)
    public void personDAOGetpersonExceptionTest() throws SQLException{
            PersonDAO dao=mock(PersonDAO.class);
            doThrow(new SQLException()).when(dao).getPersonById(0);
            dao.getPersonById(0);
    }

}
