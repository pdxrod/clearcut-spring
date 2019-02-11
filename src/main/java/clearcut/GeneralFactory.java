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

import clearcut.BatchConfiguration;
import clearcut.Person;
import clearcut.PersonRepository;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class GeneralFactory {

    public void delete() throws IOException, SQLException {
      BatchConfiguration batchConfiguration = new BatchConfiguration();
      DataSource dataSource = getMySQLDataSource();
      Connection connection = dataSource.getConnection();
      Statement statement = connection.createStatement();
      statement.execute( "DELETE FROM person;" );
      try { connection.close(); } catch (Exception e) { }
    }

    public String getFullPath( String fileName ) throws IOException {
      FileInputStream fis = null;
      File file = new File(".");
      String projectPath = file.getAbsolutePath();
      String theFile = projectPath + "/src/main/resources/" + fileName;
      file = new File(theFile);
      if( ! file.exists() ) throw new IOException( "File " + theFile + " does not exist" );
      return theFile;
    }

    public DataSource getMySQLDataSource() throws IOException {
      String propsFile = getFullPath( "application.properties" );
      File file = new File(propsFile);
      FileInputStream fis = new FileInputStream(file);
      Properties props = new Properties();
      props.load(fis);
      MysqlDataSource mysqlDS = new MysqlDataSource();
      mysqlDS.setURL(props.getProperty("spring.datasource.url"));
      mysqlDS.setUser(props.getProperty("spring.datasource.username"));
      mysqlDS.setPassword(props.getProperty("spring.datasource.password"));
      return mysqlDS;
    }

}
