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

  @Autowired
  private FileItemRepository fileItemRepository;

  public PersonTest() {
  }

  @Test
  public void testFileItemRepository() throws Exception {
    fileItemRepository.deleteAll();
    Iterable<FileItem> fileItems = fileItemRepository.findAll();
    int num = getTestHelper().countFileItems(fileItems);
    assertEquals( 0, num );
    FileItem a = new FileItem( "-rw-r--r--  1 EDGE  staff  2974 Feb 12 20:30 README.md" );
    FileItem b = new FileItem( "-rw-r--r--  1 EDGE  staff  1152 Feb 12 20:30 build.gradle" );
    List<FileItem> list = new ArrayList<FileItem>();
    list.add( a ); list.add( b );
    fileItemRepository.saveAll( list );
    fileItems = fileItemRepository.findAll();
    num = getTestHelper().countFileItems(fileItems);
    assertEquals( 2, num );
  }

  @Test
  public void testPersonRepository() throws Exception {
    personRepository.deleteAll();
    Iterable<Person> people = personRepository.findAll();
    int num = getTestHelper().countPeople(people);
    assertEquals( 0, num );
    Person a = new Person( "Fred Smith" );
    Person b = new Person( "Jim Bloggs" );
    List<Person> list = new ArrayList<Person>();
    list.add( a ); list.add( b );
    personRepository.saveAll( list );
    people = personRepository.findAll();
    num = getTestHelper().countPeople(people);
// Despite adding two people, there are still 0 of them
    assertEquals( 0, num );
  }

  @Test
  public void testGreetingController() throws Exception {
    GeneralFactory factory = getFactory();
    factory.delete();
    Iterable<Person> people = personRepository.findAll();
    int num = getTestHelper().countPeople(people);
    assertEquals( 0, num );
    GreetingController controller = new GreetingController();
    people = personRepository.findAll();
    num = getTestHelper().countPeople(people);
    assertEquals( 0, num );
    controller.startUp();
    people = personRepository.findAll();
    num = getTestHelper().countPeople(people);
    assertEquals( 0, num );
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
// There are so many ways to count a collection in Java
    people = personRepository.findAll();
    long newCount = Stream.of(people).count();
// Adding a person to the database doesn't change the count of people
    assertEquals( count, newCount );
  }

  @Test
  public void testCountPeopleInDB() throws Exception {
    int count = getTestHelper().countPeopleInDatabase();
    assertEquals( 0, count );
  }

  private TestHelper testHelper = null;
  private TestHelper getTestHelper() {
    if( testHelper == null ) testHelper = new TestHelper();
    return testHelper;
  }

  private GeneralFactory factory = null;
  private GeneralFactory getFactory() {
    if( factory == null ) factory = new GeneralFactory();
    return factory;
  }

  private class TestHelper {
    private Logger log = LoggerFactory.getLogger(TestHelper.class);

    public int countPeople( Iterable<Person> people ) {
      int num = 0;
      Iterator<Person> iterator = people.iterator();
      while(iterator.hasNext()) {
        num ++;
        iterator.next();
      }
      return num;
    }

    public int countFileItems( Iterable<FileItem> fileItems ) {
      int num = 0;
      Iterator<FileItem> iterator = fileItems.iterator();
      while(iterator.hasNext()) {
        num ++;
        iterator.next();
      }
      return num;
    }

    public int countPeopleInDatabase() throws SQLException, IOException {
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
        if( count < 1 ) log.info( "\n\n*******************\n" +
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
