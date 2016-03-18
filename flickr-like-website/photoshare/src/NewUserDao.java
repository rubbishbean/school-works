package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle the Users table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserDao {
  private static final String CHECK_EMAIL_STMT = "SELECT " +
      "COUNT(*) FROM Users WHERE email = ?";

  private static final String NEW_USER_STMT = "INSERT INTO " +
      "Users (email, password, first_name, last_name, date_of_birth, hometown, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";

  public boolean create(String email, String password, String firstname, String lastname, String dob, String hometown, String gender) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This email is already in use
        return false; 
      }
      
      try { stmt.close(); }
      catch (Exception e) { }

      stmt = conn.prepareStatement(NEW_USER_STMT);
      stmt.setString(1, email);
      stmt.setString(2, password);
      stmt.setString(3, firstname);
      stmt.setString(4, lastname);
      stmt.setString(5, dob);
      stmt.setString(6, hometown);
      stmt.setString(7, gender);
      
      
      stmt.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    }
  }

  private static final String GET_ID_BY_EMAIL = "SELECT " +
            "\"user_id\" FROM Users WHERE \"email\" = ?";
  public int getUserIdByEmail(String email) throws SQLException{
      PreparedStatement stmt = null;
      Connection conn = null;
      ResultSet rs = null;
      int userId = 0;
      try {
          conn = DbConnection.getConnection();
          stmt = conn.prepareStatement(GET_ID_BY_EMAIL);
          stmt.setString(1, email);
          rs = stmt.executeQuery();
          if (rs.next()) {
             userId = rs.getInt(1);
          }

          rs.close();
          rs = null;

          stmt.close();
          stmt = null;

          conn.close();
          conn = null;

      } catch (SQLException e) {
          e.printStackTrace();
          throw new RuntimeException(e);
      } finally {
          if (rs != null) {
              try { rs.close(); } catch (SQLException e) { ; }
              rs = null;
          }
          if (stmt != null) {
              try { stmt.close(); } catch (SQLException e) { ; }
              stmt = null;
          }
          if (conn != null) {
              try { conn.close(); } catch (SQLException e) { ; }
              conn = null;
          }
      }

      return userId;

  }

    private static final String GET_EMAIL_BY_ID = "SELECT " +
            "\"email\" FROM Users WHERE \"user_id\" = ?";
    public String getEmailById(int userId) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        String email = "";
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_EMAIL_BY_ID);
            stmt.setInt(1,userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                email = rs.getString(1);
            }

            rs.close();
            rs = null;

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { ; }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }

        return email;

    }

    private static final String SEARCH_USERS = "SELECT " +
            "\"email\" FROM Users WHERE \"email\" LIKE ?";
    public List<String> searchUsers(String username) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> searchResult = new ArrayList<String>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(SEARCH_USERS);
            stmt.setString(1,"%" + username + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                searchResult.add(rs.getString(1));
            }

            rs.close();
            rs = null;

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { ; }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }

        return searchResult;
    }

    private static final String GET_USER_FRIEND = "SELECT " +
            "\"added_id\" FROM friends WHERE \"add_id\" = ?";
    public List<Integer> getFriendList(int userId) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> friendIdList = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_USER_FRIEND);
            stmt.setInt(1,userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                friendIdList.add(rs.getInt(1));
            }

            rs.close();
            rs = null;

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { ; }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }

        return friendIdList;
    }

    private static final String GET_AVAILABLE_FRIEND = "SELECT " +
            "\"email\" FROM users U, friends F WHERE U.user_id NOT IN ((SELECT added_id FROM friends WHERE \"add_id\" = ?) UNION (SELECT user_id FROM users WHERE userId = ?))";
    public List<String> getAvailabeFriends(int userId) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<String> availableFriends = new ArrayList<String>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_AVAILABLE_FRIEND);
            stmt.setInt(1,userId);
            stmt.setInt(2,userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                availableFriends.add(rs.getString(1));
            }

            rs.close();
            rs = null;

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try { rs.close(); } catch (SQLException e) { ; }
                rs = null;
            }
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }

        return availableFriends;
    }

    private static final String CREATE_FRIEND_STMT = "INSERT INTO " +
            "friends (\"add_id\", \"added_id\") VALUES (?, ?)";
    public boolean makeFriend(int addId,int addedId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = DbConnection.getConnection();
            stmt =  conn.prepareStatement(CREATE_FRIEND_STMT);
            stmt.setInt(1,addId);
            stmt.setInt(2, addedId);
            stmt.executeUpdate();

            stmt.close();
            stmt = null;

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (stmt != null) {
                try { stmt.close(); } catch (SQLException e) { ; }
                stmt = null;
            }
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { ; }
                conn = null;
            }
        }
        return true;
    }
}
