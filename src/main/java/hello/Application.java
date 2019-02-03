package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@SpringBootApplication(exclude = BatchAutoConfiguration.class)
public class Application {

    public static void main(String[] args) throws Exception {
      String[] params = args;
      if( params.length == 0 ) {
        params = new String[ 1 ];
        params[ 0 ] = "Joe Bloggs";
      }
      Class cla$$ = Application.class;
      SpringApplication.run(cla$$, params);
    }
}
