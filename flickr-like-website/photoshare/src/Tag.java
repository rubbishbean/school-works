package photoshare;

/**
 * Created with IntelliJ IDEA.
 * User: milkman
 * Date: 11/9/15
 * Time: 5:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tag {

    private int tag_id;
    private String tag = "";
    private int picture_id;


    public Tag() {
    }

    public int getTagId() {
        return tag_id;
    }

    public void setTagId(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPictureId() {
        return picture_id;
    }

    public void setPictureId(int picture_id) {
        this.picture_id = picture_id;
    }




}
