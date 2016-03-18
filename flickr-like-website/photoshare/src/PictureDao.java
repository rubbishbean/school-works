package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class PictureDao {
    private static final String LOAD_PICTURE_STMT = "SELECT " +
            "\"caption\", \"imgdata\",\"size\", \"content_type\", \"thumbdata\", \"album_id\" FROM Pictures WHERE \"picture_id\" = ?";

    private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
            "Pictures (\"caption\", \"imgdata\", \"size\", \"content_type\", \"thumbdata\", \"album_id\") VALUES (?, ?, ?, ?, ?, ?) RETURNING picture_id";

    private static final String SAVE_ALBUMCONTIANS_STMT = "INSERT INTO " +
            "albumcontains (\"album_id\", \"picture_id\") VALUES (?, ?)";

    private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures WHERE \"album_id\" = ? ORDER BY \"picture_id\" DESC";

    public Picture load(int id) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Picture picture = null;
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                picture = new Picture();
                picture.setId(id);
                picture.setCaption(rs.getString(1));
                picture.setData(rs.getBytes(2));
                picture.setSize(rs.getLong(3));
                picture.setContentType(rs.getString(4));
                picture.setThumbdata(rs.getBytes(5));
                picture.setAlbum_id(rs.getInt(6));
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

        return picture;
    }

    public void save(Picture picture, int albumId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        int picId = 0;

        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
            stmt.setString(1, picture.getCaption());
            stmt.setBytes(2, picture.getData());
            stmt.setLong(3, picture.getSize());
            stmt.setString(4, picture.getContentType());
            stmt.setBytes(5, picture.getThumbdata());
            stmt.setInt(6, albumId);
            rs = stmt.executeQuery();
            while(rs.next()){
               picId = rs.getInt(1);
            }
            stmt.close();
            stmt = null;

            stmt = conn.prepareStatement(SAVE_ALBUMCONTIANS_STMT);
            stmt.setInt(1,albumId);
            stmt.setInt(2, picId);
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
    }

    public List<Integer> allPicturesIds(int albumId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;

        List<Integer> picturesIds = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
            stmt.setInt(1,albumId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                picturesIds.add(rs.getInt(1));
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

        return picturesIds;
    }
}