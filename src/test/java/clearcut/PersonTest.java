package clearcut;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
          Person person = new Person();
          person.setName( "Fred Smith" );
          assertEquals( person.getFirstName(), "Fred" );
          assertEquals( person.getLastName(), "Smith" );
          personRepository.save(person);
          // assert new row in people table
  }

}
