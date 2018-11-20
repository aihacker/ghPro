package pub.types;

/**
 * Created by zzl on 2015/11/6.
 */
public class IntTuple {

    private Integer a;
    private Integer b;
    private Integer c;

    public IntTuple() {
        //
    }

    public IntTuple(Integer a, Integer b, Integer c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntTuple intTuple = (IntTuple) o;

        if (a != null ? !a.equals(intTuple.a) : intTuple.a != null) return false;
        if (b != null ? !b.equals(intTuple.b) : intTuple.b != null) return false;
        return !(c != null ? !c.equals(intTuple.c) : intTuple.c != null);

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }
}
