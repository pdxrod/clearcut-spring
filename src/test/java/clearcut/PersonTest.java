package clearcut;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.stream.Stream;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Array;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private TestHelper testHelper = null;
  private TestHelper getTestHelper() {
    if( testHelper == null ) testHelper = new TestHelper();
    return testHelper;
  }

  @Test
  public void testGreetingController() throws Exception {
    int newNum = getTestHelper().showAndCountPeople();
    assertEquals( 15, newNum );
    GreetingController controller = new GreetingController();
    newNum = getTestHelper().showAndCountPeople();
    assertEquals( 15, newNum );
    controller.startUp(); // Has already been called, and does nothing on subsequent attempts
    newNum = getTestHelper().showAndCountPeople();
    assertEquals( 15, newNum );
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
          Iterable<Person> people = personRepository.findAll();
          long count = Stream.of(people).count();
          Person person = new Person();
          person.setName( "Fred Wick" );
          assertEquals( person.getFirstName(), "Fred" );
          assertEquals( person.getLastName(), "Wick" );
          Person saved = personRepository.save( person );
          assertEquals( saved, person );
          getTestHelper().showAndCountPeople();
// There are so many fun ways to count a collection in Java
          people = personRepository.findAll();
          Iterator<Person> iterator = people.iterator();
          assertTrue( iterator.hasNext() );
          long newCount = Stream.of(people).count();
          assertEquals( count + 1, newCount );
  }

  private class TestHelper {
    private Logger log = LoggerFactory.getLogger(TestHelper.class);

    public int showAndCountPeople() throws SQLException, IOException {
      int count = 0;
      File file = new File(".");
      String projectPath = file.getAbsolutePath();
      String propsFile = projectPath + "/src/main/resources/" + "application.properties";
      file = new File(propsFile);
      if( ! file.exists() ) throw new IOException( "File " + propsFile + " does not exist" );
      Properties prop = new Properties();
      FileInputStream inStream = new FileInputStream(file);
      prop.load( inStream );
      Connection con = null;

      try {
        String url = prop.getProperty("spring.datasource.url");
        String username = prop.getProperty("spring.datasource.username");
        String password = prop.getProperty("spring.datasource.password");
        con = DriverManager.getConnection(url, username, password);
        String sql = "select first_name, last_name from person";
  			PreparedStatement statement = con.prepareStatement( sql );
  			ResultSet resultset = statement.executeQuery();
        resultset.beforeFirst();
        while( resultset.next() ) {
          String firstName = resultset.getString( "first_name" );
          String lastName = resultset.getString( "last_name" );
          log.info( "\n************************ "+firstName+" "+lastName);
          ++ count;
        }
        if( count < 1 ) log.info( "\n\n\n\n*******************\n" +
                                          "THERE'S NOBODY HOME\n" );

      } finally {
        try {
          con.close();
        } catch (Exception e) {
        }
      }
      return count;
    }
  }

}
