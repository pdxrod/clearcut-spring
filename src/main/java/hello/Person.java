package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity 
public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String lastName;
    private String firstName;

    public static String notNull( String str ) {
      return str == null ? "" : str;
    }

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
  		return id;
  	}

  	public void setId(Integer id) {
  		this.id = id;
  	}

  	public String getName() {
      return notNull(getFirstName()) + " " + notNull(getLastName());
  	}

  	public void setName(String name) {
      String[] arr = name.split( " " );
  		this.firstName = arr[ 0 ];
      this.lastName = arr[ 1 ];
  	}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + ", lastName: " + lastName;
    }

}
