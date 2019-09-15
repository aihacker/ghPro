package wxgh.data.union.woman.mom;

/**
 * Created by 蔡炳炎 on 2017/9/21.
 */
public class MomImage {
    private Integer id;
    private String imagePath;

    public MomImage(){

    }

    public MomImage(Integer id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
