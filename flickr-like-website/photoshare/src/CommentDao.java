package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {
    private static final String CREATE_COMMENT_STMT = "INSERT INTO " +
            "comments (\"user_id\", \"picture_id\", \"text\", \"date_of_comment\") VALUES (?, ?, ?,  now())";

    private static final String ALL_PIC_COMMENT_STMT = "SELECT " +
            "\"comment_id\",\"user_id\", \"text\",  \"date_of_comment\" FROM comments WHERE \"picture_id\" = ?";


    public List<Comment> getAllComments(int picId) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<Comment>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(ALL_PIC_COMMENT_STMT);
            stmt.setInt(1, picId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Comment c = new Comment();
                c.setComId(rs.getInt(1));
                c.setUserId(rs.getInt(2));
                c.setPictureId(picId);
                c.setText(rs.getString(3));
                c.setCommentTime(rs.getDate(4));

                comments.add(c);
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

        return comments;
    }

    public boolean createComment(int userId,int picId,String text) {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = DbConnection.getConnection();
            stmt =  conn.prepareStatement(CREATE_COMMENT_STMT);
            stmt.setInt(1,userId);
            stmt.setInt(2, picId);
            stmt.setString(3, text);
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
