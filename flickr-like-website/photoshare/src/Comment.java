package photoshare;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: milkman
 * Date: 11/9/15
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Comment {

    private int com_id;
    private int user_id;
    private int picture_id;
    private String text = "";
    private Date date_of_comment;

    public Comment() {
    }

    public int getComId() {
        return com_id;
    }

    public void setComId(int com_id) {
        this.com_id = com_id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public int getPictureId() {
        return picture_id;
    }

    public void setPictureId(int picture_id) {
        this.picture_id = picture_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Date getCommentTime() {
        return date_of_comment;
    }

    public void setCommentTime(Date date_of_comment) {
        this.date_of_comment = date_of_comment;
    }



}
