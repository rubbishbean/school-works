package photoshare;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagDao {
    private static final String CREATE_TAGREL_STMT = "INSERT INTO " +
            "tags (\"tag\", \"picture_id\") VALUES (?, ?)";

    private static final String GET_TAG_BY_PIC_STMT = "SELECT " +
            "\"tag_id\",\"tag\" FROM tags WHERE \"picture_id\" = ?";



    public boolean createTagRelation(String tagString,int picId) {
        PreparedStatement stmt = null;
        Connection conn = null;
        String[] tags = tagString.split(";");
        List<String> tagList = Arrays.asList(tags);
        try {
            conn = DbConnection.getConnection();
            for (String tag : tagList){
                stmt =  conn.prepareStatement(CREATE_TAGREL_STMT);
                stmt.setString(1, tag);
                stmt.setInt(2, picId);
                stmt.executeUpdate();
                stmt.close();
                stmt = null;
            }

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

    public List<Tag> getAllTagsOfPic(int picId) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Tag> tags = new ArrayList<Tag>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_TAG_BY_PIC_STMT);
            stmt.setInt(1, picId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTagId(rs.getInt(1));
                tag.setTag(rs.getString(2));
                tag.setPictureId(picId);

                tags.add(tag);
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

        return tags;
    }


    private static final String GET_USER_ASSOCIATED_PICS = "SELECT " +
            "T.picture_id FROM tags T WHERE T.tag = ? and T.picture_id IN (SELECT P.picture_id FROM albums A, pictures P WHERE A.owner_id = ? AND A.album_id = P.album_id)";

    public List<Integer> getUserPicByTag(String tag, int user_id) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> pictureIds = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_USER_ASSOCIATED_PICS);
            stmt.setString(1,tag);
            stmt.setInt(2,user_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pictureIds.add(rs.getInt(1));
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

        return pictureIds;
    }


    private static final String GET_ALL_ASSOCIATED_PICS = "SELECT " +
            "\"picture_id\" FROM tags WHERE \"tag\" = ?";

    public List<Integer> getAllPicByTag(String tag) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        List<Integer> pictureIds = new ArrayList<Integer>();
        try {
            conn = DbConnection.getConnection();
            stmt = conn.prepareStatement(GET_ALL_ASSOCIATED_PICS);
            stmt.setString(1,tag);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pictureIds.add(rs.getInt(1));
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

        return pictureIds;
    }




    public List<Integer> searchTags(String tagString) throws SQLException {
        String[] tags = tagString.split(" ");
        List<String> tagList = new ArrayList(Arrays.asList(tags));
        List<Integer> pictureIds = new ArrayList<Integer>();
        List<Integer> container = new ArrayList<Integer>();

        String tagOne = tagList.get(0);
        List<Integer> filter = getAllPicByTag(tagOne);
        tagList.remove(0);
        for(String tag : tagList){
            List<Integer> picForEachTag = getAllPicByTag(tag);
            for(Integer p : filter){
                if(picForEachTag.contains(p)){
                    container.add(p);
                }
            }
            filter.clear();
            filter.addAll(container);
            container.clear();

        }
        pictureIds.addAll(filter);

        return pictureIds;
    }

}
