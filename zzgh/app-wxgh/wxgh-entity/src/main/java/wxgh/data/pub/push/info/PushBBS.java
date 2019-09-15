package wxgh.data.pub.push.info;

/**
 * Created by Administrator on 2017/8/24.
 */
public class PushBBS {

    private Integer id;
    private String title;
    private String content;
    private String image; //封面图片

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
