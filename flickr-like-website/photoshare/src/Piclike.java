package photoshare;

/**
 * Created with IntelliJ IDEA.
 * User: milkman
 *
 */
public class Piclike {
    private int user_id;
    private int picture_id;

    public Piclike(){

    }

    public int getUserId(){
        return user_id;
    }

    public void setUserId(int user_id){
        this.user_id = user_id;
    }

    public int getPictureId(){
        return picture_id;
    }

    public void setPictureId(int picture_id){
        this.picture_id = picture_id;
    }
}
