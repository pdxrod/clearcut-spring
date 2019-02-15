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

  private String[] getValues() {
    return values;
  }

  public String[] values() {
    return values;
  }

  private void setValues( String[] values ) {
    this.values = values;
    this.mode =  values[ 0 ];
    this.links = values[ 1 ];
    this.owner = values[ 2 ];
    this.grp =   values[ 3 ];
    this.size =  values[ 4 ];
    this.month = values[ 5 ];
    this.day =   values[ 6 ];
    this.time =  values[ 7 ];
    this.name =  values[ 8 ];
  }

  private String mode;
  public String getMode() { return this.mode; } public void setMode( String mode ) { this.mode = mode; }

  private String links;
  public String getLinks() { return this.links; } public void setLinks( String links ) { this.links = links; }

  private String owner;
  public String getOwner() { return this.owner; } public void setOwner( String owner ) { this.owner = owner; }

  private String grp;
  public String getGrp() { return this.grp; } public void setGrp( String grp ) { this.grp = grp; }

  private String size;
  public String getSize() { return this.size; } public void setSize( String size ) { this.size = size; }

  private String month;
  public String getMonth() { return this.month; } public void setMonth( String month ) { this.month = month; }

  private String day;
  public String getDay() { return this.day; } public void setDay( String day ) { this.day = day; }

  private String time;
  public String getTime() { return this.time; } public void setTime( String time ) { this.time = time; }

  private String name;
  public String getName() { return this.name; } public void setName( String name ) { this.name = name; }

  public static String notNull( String str ) {
    return str == null ? "" : str;
  }

  public static boolean mt( String str ) {
    return str == null || str.trim() == "";
  }

  public FileItem() {
  }

  public FileItem(String[] values) {
    this();
    this.setValues( values );
  }

  public FileItem(String values) {
    this();
    String[] arr = values.split( "\\s+" );
    this.setValues( arr );
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
