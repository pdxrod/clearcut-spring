package clearcut;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.*;

public class PersonTest extends TestCase {

	public PersonTest() {
	}

	public void testPersonName() throws Exception {
    Person person = new Person();
    person.setName( "John Smith" );
    assertEquals( person.getFirstName(), "John" );
    assertEquals( person.getLastName(), "Smith" );

    person.setName( "Fred" );
    assertEquals( person.getFirstName(), "Fred" );
    assertEquals( person.getLastName(), "Bloggs" );
	}

}
