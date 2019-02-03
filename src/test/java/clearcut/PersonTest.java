package clearcut;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonTest extends TestCase {

  public PersonTest() {
	}

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

}
