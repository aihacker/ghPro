package wxgh.param.pub;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2017/10/16
 * time：9:56
 * version：V1.0
 * Description：
 */
public class CarouselParam {

    private Integer type;
    private Integer display;

    public CarouselParam(Integer type) {
        this.type = type;
    }

    public static CarouselParam work() {
        return new CarouselParam(1);
    }

    public static CarouselParam woman() {
        return new CarouselParam(2);
    }

    public static CarouselParam party() {
        return new CarouselParam(3);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }
}
