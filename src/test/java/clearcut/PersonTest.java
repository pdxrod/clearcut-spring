package clearcut;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Array;
import javax.sql.DataSource;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// Thanks to https://stackoverflow.com/questions/23435937/how-to-test-spring-data-repositories - 'heez', March 2017
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonTest extends TestCase {

  @Autowired
  private PersonRepository personRepository;

  public PersonTest() {
  }

  @Test
  public void testPersonName() throws Exception {
          Person person = new Person();
          person.setName( "John                Smith" );
          assertEquals( person.getFirstName(), "John" );
          assertEquals( person.getLastName(), "Smith" );
          assertEquals( person.toString(), "firstName: John, lastName: Smith" );

          person.setName( "Fred" );
          assertEquals( person.getFirstName(), "Fred" );
          assertEquals( person.getLastName(), "Bloggs" );
          assertEquals( person.toString(), "firstName: Fred, lastName: Bloggs" );
  }

  @Test
  public void testPersonAdd() throws Exception {
          long count = personRepository.count();
          Person person = new Person();
          person.setName( "Fred Wick" );
          assertEquals( person.getFirstName(), "Fred" );
          assertEquals( person.getLastName(), "Wick" );
          personRepository.save( person );
// How to find out if there are any rows using Java
          Iterable<Person> people = personRepository.findAll();
          Iterator<Person> iterator = people.iterator();
          assertTrue( iterator.hasNext() ); 
          assertEquals( count + 1, personRepository.count() );
  }

  private int showPeople() throws SQLException {
    Connection con = null;
    int count = 0;
    try {
      con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springboot_simple", "root", "");
      String sql = "select first_name, last_name from person";
			PreparedStatement statement = con.prepareStatement( sql );
			ResultSet resultset = statement.executeQuery();
      resultset.first();
      while( resultset.next() ) {
        String firstName = resultset.getString( "first_name" );
        String lastName = resultset.getString( "last_name" );
        System.out.println( "\n\n\n\n************************ "+firstName+" "+lastName);
        ++ count;
      }
    } finally {
      try {
        con.close();
      } catch (Exception e) {
      }
    }
    return count;
  }

}
