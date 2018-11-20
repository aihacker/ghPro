package pub.graphics;

import java.awt.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-19
 */
public class SimpleRect {

    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public SimpleRect() {

    }

    public SimpleRect(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getWidth() {
        return x2 - x1;
    }

    public int getHeight() {
        return y2 - y1;
    }

    public SimpleRect adjust(SimpleRect rcOffset) {
        return new SimpleRect(x1 + rcOffset.x1, y1 + rcOffset.y1, x2 + rcOffset.x2, y2 + rcOffset.y2);
    }

    public boolean intersects(SimpleRect rect) {
        return (x1 > rect.x1 && x1 < rect.x2 || x2 > rect.x1 && x2 < rect.x2) &&
                (y1 > rect.y1 && y1 < rect.y2 || y2 > rect.y1 && y2 < rect.y2);
    }

    public void offset(Point ptOffset) {
        x1 += ptOffset.x;
        x2 += ptOffset.x;
        y1 += ptOffset.y;
        y2 += ptOffset.y;
    }

    public Point center() {
        return new Point((x2 + x1) / 2, (y2 + y1) / 2);
    }
}
