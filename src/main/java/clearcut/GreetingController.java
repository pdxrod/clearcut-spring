package clearcut;

import javax.sql.DataSource;
import javax.validation.Valid;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import clearcut.Person;
import clearcut.PersonRepository;
import clearcut.FileItemRepository;
import clearcut.GeneralFactory;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@RestController
public class GreetingController {
  private static boolean started = false;
  private final String COMMA = ",";

  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private FileItemRepository fileItemRepository;
  private static final String TEMPLATE = "Hello, %s!";

  public GreetingController() throws Exception {
    startUp();
  }

  @RequestMapping("/greeting")
  public HttpEntity<Greeting> greeting(
  @RequestParam(value = "name", required = false, defaultValue = "Joe Bloggs") String name) {
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

    GeneralFactory factory = new GeneralFactory();
    DataSource dataSource = factory.getMySQLDataSource();
    BatchConfiguration batchConfiguration = new BatchConfiguration();
    JdbcBatchItemWriter<FileItem> writer = batchConfiguration.writer( dataSource );
    FileItemItemProcessor processor = batchConfiguration.processor();
  }

}
