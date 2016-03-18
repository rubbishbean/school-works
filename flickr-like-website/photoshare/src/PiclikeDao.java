package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: milkman
 * Date: 11/10/15
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class PiclikeDao {

    private static final String ADD_LIKE_STMT = "INSERT INTO " +
            "likepicture (\"user_id\", \"picture_id\") VALUES (?, ?)";

    private static final String CHECK_ALREADY_LIKE = "SELECT " +
            "\"user_id\" FROM likepicture WHERE \"user_id\" = ? AND \"picture_id\" = ?";

    private static final String GET_ALL_LIKE_OF_PIC = "SELECT " +
            "\"user_id\" FROM likepicture WHERE \"picture_id\" = ?";

    public boolean addLike(int user_id,int pic_id){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DbConnection.getConnection();
            stmt =  conn.prepareStatement(CHECK_ALREADY_LIKE);
            stmt.setInt(1,user_id);
            stmt.setInt(2, pic_id);
            rs = stmt.executeQuery();

            if (rs.next()){
                stmt.close();
                stmt = null;
                rs.close();
                rs = null;
                conn.close();
                conn = null;
                return false;
            }else{
                stmt.close();
                stmt = null;

                stmt =  conn.prepareStatement(ADD_LIKE_STMT);
                stmt.setInt(1,user_id);
                stmt.setInt(2, pic_id);
                stmt.executeUpdate();
            }



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
    private static final String GET_USER_EMAIL = "SELECT " +
            "\"email\" FROM users WHERE \"user_id\" = ?";

    public List<String> getAllLikers(int picId) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> userIds = new ArrayList<Integer>();
        List<String> emails = new ArrayList<String>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALL_LIKE_OF_PIC);
            stmt.setInt(1, picId);
            rs = stmt.executeQuery();
            while (rs.next()) {
               Integer id = rs.getInt(1);
               userIds.add(id);
            }
            rs.close();
            rs = null;

            stmt.close();
            stmt = null;

            for(Integer userid : userIds){
                stmt = conn.prepareStatement(GET_USER_EMAIL);
                stmt.setInt(1,userid);
                rs = stmt.executeQuery();
                if (rs.next()){
                    String email = rs.getString(1);
                    emails.add(email);
                }
                rs.close();
                rs = null;
                stmt.close();
                stmt = null;
            }


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

        return emails;
    }

    private static final String COUNT_ALL_LIKE_OF_PIC = "SELECT COUNT(user_id) FROM likepicture WHERE \"picture_id\" = ?";
    public int countLike(int picId) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int countlike = 0;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(COUNT_ALL_LIKE_OF_PIC);
            stmt.setInt(1, picId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                countlike = rs.getInt(1);
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

        return countlike;
    }
}
