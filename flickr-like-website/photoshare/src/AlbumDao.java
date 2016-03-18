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
 * Date: 11/8/15
 * Time: 11:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlbumDao {

    private static final String LOAD_ALBUM_STMT = "SELECT " +
            "\"name\", \"owner_id\", \"date_of_creation\" FROM Albums WHERE \"album_id\" = ?";


    private static final String CREATE_ALBUM_STMT = "INSERT INTO " +
            "albums (\"name\", \"owner_id\", \"date_of_creation\") VALUES (?, ?, now())";

    private static final String GET_ALBUM_PICS = "SELECT " +
            "\"picture_id\" FROM albumcontains WHERE \"album_id\" = ?";

    private static final String ALL_USR_ALBUMS_STMT = "SELECT " +
            "\"album_id\",\"name\", \"owner_id\", \"date_of_creation\" FROM albums WHERE \"owner_id\" = ?";

    public Album getAlbumById(int id) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Album a = new Album();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(LOAD_ALBUM_STMT);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                a.setId(id);
                a.setName(rs.getString(1));
                a.setOwnerId(rs.getInt(2));
                a.setCreateTime(rs.getDate(3));
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
        return a;
    }

    public List<Integer> getAlbumPicId(int id) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> picIds = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALBUM_PICS);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
               picIds.add(rs.getInt(1));
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

        return picIds;
    }

    public List<Album> getAllAlbums(int owner_id) throws SQLException{
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Album> albumsList = new ArrayList<Album>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(ALL_USR_ALBUMS_STMT);
            stmt.setInt(1,owner_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Album a = new Album();
                a.setId(rs.getInt(1));
                a.setName(rs.getString(2));
                a.setOwnerId(rs.getInt(3));
                a.setCreateTime(rs.getDate(4));
                albumsList.add(a);
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

        return albumsList;
    }

    public boolean createAlbum(String name,int o_id) {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = DbConnection.getConnection();
            stmt =  conn.prepareStatement(CREATE_ALBUM_STMT);
            stmt.setString(1,name);
            stmt.setInt(2, o_id);
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

    private static final String DEL_ALBUM_STMT = "DELETE FROM albums WHERE \"album_id\" = ?";    //with delete on cascade, the row in albumcontains will be delete automatically
   // private static final String DEL_IN_ALBUMCONTAINS_STMT = "DELETE FROM albumcontains WHERE \"album_id\" = ?";
   // private static final String DEL_ALBUM_PICS = "DELETE FROM pictures WHERE \"picture_id\" = ?";
    private static final String DEL_PIC_COMTS = "DELETE FROM comments WHERE \"picture_id\" = ?";

    public void deleteAlbum(int id) {
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        Connection conn = null;
        List<Integer> albumPicsIds = new ArrayList<Integer>();

        try {
            conn = DbConnection.getConnection();
            albumPicsIds = getAlbumPicId(id);
            stmt = conn.prepareStatement(DEL_PIC_COMTS);
            for(Integer pictureId : albumPicsIds){
                stmt.setInt(1,pictureId);
                stmt.executeUpdate();
            }

            stmt.close();
            stmt = null;

            stmt2 = conn.prepareStatement(DEL_ALBUM_STMT);
            stmt2.setInt(1,id);
            stmt2.executeUpdate();
            stmt2.close();

            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
