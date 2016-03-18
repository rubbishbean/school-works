package photoshare;

/**
 * A bean that handles new user data
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserBean {
  private int user_id ;
  private String email = "";
  private String password1 = "";
  private String password2 = "";
  private String firstName = "";
  private String lastName = "";
  private String dob = "";
  private String hometown = "";
  private String gender = "";

  public String saySomething() {
    System.out.println("Hello!");
    return "Test";
  }

  public int getUserId(){
      return user_id;
  }

  public void setUserId(int user_id){
      this.user_id = user_id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword1() {
    return password1;
  }

  public String getPassword2() {
    return password2;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public String getFirstName(){
      return firstName;
  }

  public void setFirstName(String firstName){
      this.firstName = firstName;
  }

  public String getLastName(){
      return lastName;
  }

  public void setLastName(String lastName){
      this.lastName = lastName;
  }
}
