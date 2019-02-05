package clearcut;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.stereotype.Controller;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.transaction.PlatformTransactionManager;

import clearcut.Person;
import clearcut.PersonRepository;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@RestController
public class GreetingController {
  private static boolean started = false;

  @Autowired
  private PersonRepository personRepository;
  private static final String TEMPLATE = "Hello, %s!";

  public GreetingController() throws Exception {
    startUp();
  }

  @RequestMapping("/greeting")
  public HttpEntity<Greeting> greeting(
  @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

    Greeting greeting = new Greeting(String.format(TEMPLATE, name));
    greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());
    return new ResponseEntity<>(greeting, HttpStatus.OK);
  }

  @GetMapping(path="/add")     // Map ONLY GET Requests
  public @ResponseBody String addNewPerson (@RequestParam String name) {

    Person n = new Person();
    n.setName(name);
    personRepository.save(n);
    return String.format( "{\"name\": \"%s\", \"saved\": true}\n", name );
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Person> getAllPersons() {
    return personRepository.findAll();
  }

  public void startUp() throws Exception {
    if( started ) return;
    started = true;
    BatchConfiguration batchConfiguration = new BatchConfiguration();
    DataSource dataSource = new MyDataSourceFactory().getMySQLDataSource();
    JdbcBatchItemWriter<Person> writer = batchConfiguration.writer( dataSource );
    PersonItemProcessor processor = batchConfiguration.processor();
    Person person = null;
    List<Person> list = new ArrayList<Person>();

    File file = new File(".");
    String projectPath = file.getAbsolutePath();
    String csvFile = projectPath + "/src/main/resources/" + "sample-data.csv";
    BufferedReader br = new BufferedReader(new FileReader(csvFile));
    String line = null;
    while( (line = br.readLine()) != null ) {
      if( line.indexOf( "," ) > 0 ) {
        String[] text = line.split( "," );
        Person p = new Person( text[0], text[1] );
        p = processor.process( p );
        list.add( p );
      }
    }

    writer.afterPropertiesSet(); // You have to do this, or you get a null pointer exception
    writer.write( list );
    try {
      br.close();
    } catch (Exception e) { }    // There's not much you can do about this

    // FlatFileItemReader<Person> reader = batchConfiguration.reader();
    // StepBuilderFactory stepBuilderFactory = batchConfiguration.stepBuilderFactory;
    // StepBuilder stepBuilder = stepBuilderFactory.get("step1"); // the stepBuilderFactory is null at this point
    // SimpleStepBuilder simpleStepBuilder = stepBuilder.<Person, Person>chunk( BatchConfiguration.CHUNK_SIZE );
    // simpleStepBuilder = simpleStepBuilder.reader(reader);
    // simpleStepBuilder = simpleStepBuilder.processor(processor);
    // simpleStepBuilder = simpleStepBuilder.writer(writer);
    // simpleStepBuilder.build();
  }

  private class MyDataSourceFactory {

    public DataSource getMySQLDataSource() throws IOException {
      Properties props = new Properties();
      FileInputStream fis = null;
      MysqlDataSource mysqlDS = null;
      File file = new File(".");
      String projectPath = file.getAbsolutePath();
      String propsFile = projectPath + "/src/main/resources/" + "application.properties";
      file = new File(propsFile);
      if( ! file.exists() ) throw new IOException( "File " + propsFile + " does not exist" );
      fis = new FileInputStream(file);
      props.load(fis);
      mysqlDS = new MysqlDataSource();
      mysqlDS.setURL(props.getProperty("spring.datasource.url"));
      mysqlDS.setUser(props.getProperty("spring.datasource.username"));
      mysqlDS.setPassword(props.getProperty("spring.datasource.password"));
      return mysqlDS;
    }
  }

}
