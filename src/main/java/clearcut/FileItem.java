package clearcut;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FileItem {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  private String[] values;
  private String mode;
  private String links;
  private String owner;
  private String grp;
  private String size;
  private String month;
  private String day;
  private String time;
  private String name;

  public static String notNull( String str ) {
    return str == null ? "" : str;
  }

  public static boolean mt( String str ) {
    return str == null || str.trim() == "";
  }

  public FileItem() {
  }

  public FileItem(String[] fileListing) {
    this();
    this.setValues( fileListing );
  }

  public FileItem(String fileListing) {
    this();
    String[] arr = fileListing.split( "\\s+" );
    this.setValues( arr );
  }

  public String[] getValues() {
    return values;
  }

  public void setValues( String[] fileListing ) {
    this.values = fileListing;
    this.mode =  fileListing[ 0 ];
    this.links = fileListing[ 1 ];
    this.owner = fileListing[ 2 ];
    this.grp =   fileListing[ 3 ];
    this.size =  fileListing[ 4 ];
    this.month = fileListing[ 5 ];
    this.day =   fileListing[ 6 ];
    this.time =  fileListing[ 7 ];
    this.name =  fileListing[ 8 ];
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "name: " + name;
  }

}
