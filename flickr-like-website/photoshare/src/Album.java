package photoshare;

import java.util.Date;

public class Album {
  private int al_id;
  private String name = "";
  private Date doc; //date of creation
  private int owner_id;

  public Album() {
  }

  public int getId() {
    return al_id;
  }

  public void setId(int id) {
    this.al_id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public Date getCreateTime() {
    return doc;
  }

  public void setCreateTime(Date doc) {
    this.doc = doc;
  }

  public int getOwnerId() {
    return owner_id;
  }

  public void setOwnerId(int owner_id) {
    this.owner_id = owner_id;
  }


}
